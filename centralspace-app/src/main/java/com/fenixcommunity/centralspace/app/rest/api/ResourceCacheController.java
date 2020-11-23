package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.app.rest.caller.RestTemplateHelper.createRestEntity;
import static lombok.AccessLevel.PRIVATE;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.app.rest.caller.RestTemplateHelper;
import com.fenixcommunity.centralspace.app.rest.dto.resource.ExternalResourceDto;
import com.fenixcommunity.centralspace.app.rest.dto.resource.InternalResourceDto;
import com.fenixcommunity.centralspace.app.service.resource.ResourceCacheService;
import com.fenixcommunity.centralspace.utilities.common.FileDevTool;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.web.WebTool;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller @RequestMapping("/api/resource-cache")
@Timed
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ResourceCacheController {
    private final ResourceCacheService resourceCacheService;
    private final RestCallerStrategy restCallerStrategy;
    private final String restPath;

    public ResourceCacheController(final ResourceCacheService resourceCacheService, final RestCallerStrategy restCallerStrategy,
                                   @Value("${path.rest}") final String restPath) {
        this.resourceCacheService = resourceCacheService;
        this.restCallerStrategy = restCallerStrategy;
        this.restPath = restPath;
    }

    @GetMapping("/**")
    @ApiOperation(value = "Get resource: example path http://localhost:8088/app/api/resource-cache/static/img/Top.png")
    public @ResponseBody byte[] getFileByPath(final HttpServletRequest request) throws IOException {
        final String extractedPath = WebTool.extractUriPath(request);
        final Resource resource = resourceCacheService.getInternalResource(extractedPath);
        return IOUtils.toByteArray(FileUtils.openInputStream(resource.getFile()));
    }

    @PostMapping("/available")
    public ResponseEntity<Boolean> isAvailable(@RequestBody final InternalResourceDto internalResourceDto) {
        final var inputImageUrl = resourceCacheService.getInternalImagePath(internalResourceDto);
        return ResponseEntity.ok(resourceCacheService.isInternalResourceExists(inputImageUrl));
    }

    @GetMapping("/test/{fileName}.{fileFormat}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Boolean> test(@PathVariable("fileName") final String fileName, @PathVariable("fileFormat") final FileFormat fileFormat) {
        final InternalResourceDto internalResourceDto = new InternalResourceDto(fileName, fileFormat);
        final RestTemplate restTemplate = restCallerStrategy.getRetryRestTemplate();
        final URI requestedAvailableUrl = URI.create(restPath + "/resource-cache/available");
        final HttpEntity<InternalResourceDto> restEntity = createRestEntity(internalResourceDto);
        final Boolean response = restTemplate
                                .exchange(requestedAvailableUrl, HttpMethod.POST, restEntity, Boolean.class)
                                .getBody();
        final Boolean responseOtherWay = restTemplate
                                         .postForObject(requestedAvailableUrl, restEntity, Boolean.class);
        // download large file
        final URI requestedFileUrl = URI.create(restPath + "/resource-cache/static/img/Top.png");
        final File fileResponse = RestTemplateHelper.getLargeFileRequest(restTemplate, requestedFileUrl);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/clear-all-cache")
    public ResponseEntity<Boolean> clearAllCache() {
        return ResponseEntity.ok(resourceCacheService.clearAllCache());
    }

    @GetMapping("/clear-internal-resource/{pathOfResource}")
    @ApiOperation(value = "Clear resource in cache: example path -> /static/img/Top.png")
    public ResponseEntity<Boolean> clearInternalResource(@PathVariable("pathOfResource") final String pathOfResource) {
        return ResponseEntity.ok(resourceCacheService.clearInternalResource(pathOfResource));
    }

    @GetMapping("/show-all-caches/{cacheObject}/{storeCacheName}")
    public ResponseEntity<Boolean> showAllCaches(@PathVariable("cacheObject") final Object cacheObject,
                                                 @PathVariable("storeCacheName") final String storeCacheName) {
        return ResponseEntity.ok(resourceCacheService.isCacheObjectExist(cacheObject, storeCacheName));
    }

    @PostMapping(value = "/download-file-from-url", consumes={"application/json"})
    public @ResponseBody byte[] downloadFileFromUrl(@RequestBody @NotNull final ExternalResourceDto externalResourceDto) {
        final File file = resourceCacheService.downloadFileFromUrl(externalResourceDto.getUrlPath(), externalResourceDto.getDestinationFilePath());
        return FileDevTool.getBytesFromFile(file);
    }

    @PostMapping(value = "/download-file-with-resume", consumes={"application/json"})
    public @ResponseBody byte[] downloadFileWithResume(@RequestBody @NotNull final ExternalResourceDto externalResourceDto){
        final File file = resourceCacheService.downloadFileWithResume(externalResourceDto.getUrlPath(), externalResourceDto.getDestinationFilePath());
        return FileDevTool.getBytesFromFile(file);
    }


}