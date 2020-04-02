package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;

import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.app.rest.caller.RestTemplateHelper;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.fenixcommunity.centralspace.utilities.web.WebTool;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller @RequestMapping("/api/resource")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class ResourceController {

    private final ResourceLoaderTool resourceTool;
    private final RestCallerStrategy restCallerStrategy;

    @GetMapping("/**")
    public @ResponseBody
    byte[] getFileByPath(final HttpServletRequest request) throws IOException {
        final String extractedPath = WebTool.extractUriPath(request);
        final Resource resource = resourceTool.loadResourceByPath(extractedPath);
        return IOUtils.toByteArray(FileUtils.openInputStream(resource.getFile()));
    }

    @GetMapping("/test")
    @Secured({"ROLE_ADMIN"})
    public boolean test(@PathVariable("fileName") String fileName, @PathVariable("fileFormat") FileFormat fileFormat) {
        final var inputImageUrl = resourceTool.getResourceProperties().getImageUrl()
                + fileName + DOT + fileFormat.getSubtype();
        final RestTemplate restTemplateRetryWrapper = restCallerStrategy.getRestTemplate();

        final ResponseEntity<byte[]> responseEntity = restTemplateRetryWrapper
                .exchange(URI.create(inputImageUrl), HttpMethod.GET, RestTemplateHelper.createHttpEntityWithHeaders(MediaType.ALL), byte[].class);

        return responseEntity.getBody() != null &&  responseEntity.getBody().length > 1;
    }
}
