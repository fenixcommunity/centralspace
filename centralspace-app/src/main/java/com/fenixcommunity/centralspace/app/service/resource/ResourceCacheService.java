package com.fenixcommunity.centralspace.app.service.resource;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static java.util.Collections.emptyMap;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.fenixcommunity.centralspace.app.configuration.caching.CachingConfig;
import com.fenixcommunity.centralspace.app.rest.dto.resource.InternalResourceDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.utilities.common.FileDevTool;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ExternalResourceLoader;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResourceLoader;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
@CacheConfig(cacheNames = CachingConfig.CENTRALSPACE_CACHE)
public class ResourceCacheService {
    private static final String CENTRALSPACE_CACHE = CachingConfig.CENTRALSPACE_CACHE;
    private final InternalResourceLoader internalResourceLoader;
    private final ExternalResourceLoader externalResourceLoader;
    private final CacheManager cacheManager;

//  @CachePut(value="Resource", condition="#extractedPath=='correctPath'")
//  @CachePut always invoke method and updates cache, cacheable only once for key
    @Cacheable(key = "#a0", unless = "#result.exists() != true") // sync = true not works, I think that should be selected @EnableCaching(proxyTargetClass = true)
    @Transactional(readOnly = true)
    public Resource getInternalResource(@NonNull final String extractedPath) {
        return internalResourceLoader.loadResourceByPath(extractedPath);
    }

    public Map<String, Boolean> handleFormFilesUploadRequest(final HttpServletRequest request) {
        final boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            try {
                final Stream<Part> requestPartsStream = request.getParts().stream().filter(part -> part.getSize() > 0);
                return uploadFiles(requestPartsStream);
            } catch (IOException | ServletException e) {
                return emptyMap();
            }
        }
        return emptyMap();
    }

    private Map<String, Boolean> uploadFiles(final Stream<Part> requestParts) {
        final Map<String, Boolean> resultsForFilenameUploading = new LinkedHashMap<>();
        final String uploadFileUrl = internalResourceLoader.getResourceProperties().getUploadFileUrl();
        requestParts.forEach(uploadingFilePart -> {
                    final long fileSize = uploadingFilePart.getSize();
                    final String fileName = uploadingFilePart.getSubmittedFileName();
                    if (fileSize != 0 && isNotBlank(fileName)) {
                        final File fileToUpload = FileDevTool.createNewOutputFile(uploadFileUrl + fileName);
                        try {
                            if (fileToUpload == null) {
                                throw new IOException("init file to upload not created");
                            }
                            try (final OutputStream out = new FileOutputStream(fileToUpload)) {
                                IOUtils.copy(uploadingFilePart.getInputStream(), out);
                                resultsForFilenameUploading.put(fileName, true);
                            }
                        } catch (IOException e) {
                            resultsForFilenameUploading.put(fileName, false);
                        }
                    }
                }
        );


        return resultsForFilenameUploading;
    }

    public boolean isInternalResourceExists(@NonNull final String extractedPath) {
        try {
            return internalResourceLoader.loadResourceByPath(extractedPath).getFile().exists();
        } catch (FileNotFoundException e) {
            log.warn("Problem with finding the file");
            return false;
        } catch (Exception e) {
            throw new ServiceFailedException("Problem during finding resource");
        }
    }

    // now global, possible -> value = "internalImagePaths",
   //  @Cacheable(value = CENTRALSPACE_CACHE, keyGenerator = "appKeyGenerator")
    @Cacheable(key = "#internalResourceDto.fileName", condition = "#internalResourceDto.fileName == 'Top'")
    public String getInternalImagePath(@NonNull final InternalResourceDto internalResourceDto) {
        return internalResourceLoader.getResourceProperties().getImageUrl()
                + internalResourceDto.getFileName() + DOT + internalResourceDto.getFileSubType();
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CENTRALSPACE_CACHE, allEntries = true)})
    public boolean clearAllCache() {
        return true;
    }

    @CacheEvict(cacheNames = CENTRALSPACE_CACHE, key = "#a0", allEntries = true)
    public boolean clearInternalResource(final String resourcePath) {
        return true;
    }

    public boolean isCacheObjectExist(final Object cacheObject, final String storeCacheName) {
        final Cache cache = cacheManager.getCache(storeCacheName);
        if (cache == null) {
            throw new IllegalArgumentException("Cache '" + storeCacheName + "' not found");
        }
        final Cache.ValueWrapper valueWrapper = cache.get(cacheObject);
        return valueWrapper != null && valueWrapper.get() != null;
    }

    public File downloadFileFromUrl(final String urlPath, final String destinationFilePath) {
        return externalResourceLoader.downloadFileFromUrl(urlPath, destinationFilePath);
    }

    public File downloadFileWithResume(final String downloadUrl, final String savedFilePath) {
        return externalResourceLoader.downloadFileWithResume(downloadUrl, savedFilePath);
    }
}