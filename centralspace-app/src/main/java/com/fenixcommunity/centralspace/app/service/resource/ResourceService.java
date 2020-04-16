package com.fenixcommunity.centralspace.app.service.resource;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.io.FileNotFoundException;

import com.fenixcommunity.centralspace.app.configuration.caching.CachingConfig;
import com.fenixcommunity.centralspace.app.rest.dto.aws.InternalResourceDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
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
public class ResourceService {
    private static final String CENTRALSPACE_CACHE = CachingConfig.CENTRALSPACE_CACHE;
    private final ResourceLoaderTool resourceTool;
    private final CacheManager cacheManager;

//  @CachePut(value="Resource", condition="#extractedPath=='correctPath'")
//  @CachePut always invoke method and updates cache, cacheable only once for key
    @Cacheable(key = "#a0", unless = "#result.exists() != true", sync = true)
    //@Cacheable(key = "#root.methodName", sync = true, unless = "result != null")
    @Transactional(readOnly = true)
    public Resource getInternalResource(final String extractedPath) {
        return resourceTool.loadResourceByPath(extractedPath);
    }

    public boolean isInternalResourceExists(final String extractedPath) {
        try {
            return resourceTool.loadResourceByPath(extractedPath).getFile().exists();
        } catch (FileNotFoundException e) {
            log.warn("Problem with finding the file");
            return false;
        } catch (Exception e) {
            throw new ServiceFailedException("Problem during finding resource");
        }
    }

    // now global, possible -> value = "internalImagePaths",
    @Cacheable(key = "#internalResourceDto.fileName", condition = "#internalResourceDto.fileName == 'Top'")
    public String getInternalImagePath(final InternalResourceDto internalResourceDto) {
        return resourceTool.getResourceProperties().getImageUrl()
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
}