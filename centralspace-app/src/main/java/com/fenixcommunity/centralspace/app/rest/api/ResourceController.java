package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.app.rest.caller.RestTemplateHelper.createRestEntity;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;

import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.app.rest.dto.aws.InternalResourceDto;
import com.fenixcommunity.centralspace.app.service.resource.ResourceService;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.web.WebTool;
import io.micrometer.core.annotation.Timed;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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

@Controller @RequestMapping("/api/resource")
@Timed
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ResourceController {
    private final ResourceService resourceService;
    private final RestCallerStrategy restCallerStrategy;
    private final String restPath;

    public ResourceController(final ResourceService resourceService, final RestCallerStrategy restCallerStrategy,
                              @Value("${rest.path}") final String restPath) {
        this.resourceService = resourceService;
        this.restCallerStrategy = restCallerStrategy;
        this.restPath = restPath;
    }

    @GetMapping("/**")
    public @ResponseBody
    byte[] getFileByPath(final HttpServletRequest request) throws IOException {
        final String extractedPath = WebTool.extractUriPath(request);
        final Resource resource = resourceService.getInternalResource(extractedPath);
        return IOUtils.toByteArray(FileUtils.openInputStream(resource.getFile()));
    }

    @PostMapping("/available")
    public ResponseEntity<Boolean> isAvailable(@RequestBody final InternalResourceDto internalResourceDto) {
        final var inputImageUrl = resourceService.getInternalImagePath(internalResourceDto);
        return ResponseEntity.ok(resourceService.isInternalResourceExists(inputImageUrl));
    }


    @GetMapping("/test/{fileName}.{fileFormat}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Boolean> test(@PathVariable("fileName") final String fileName, @PathVariable("fileFormat") final FileFormat fileFormat) {
        final InternalResourceDto internalResourceDto = new InternalResourceDto(fileName, fileFormat);
        final RestTemplate restTemplate = restCallerStrategy.getRetryRestTemplate();
        final Boolean response = restTemplate
                .exchange(URI.create(restPath + "/resource/available"), HttpMethod.POST, createRestEntity(internalResourceDto), Boolean.class)
                .getBody();

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }
}