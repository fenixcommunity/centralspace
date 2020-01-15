package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.fenixcommunity.centralspace.utilities.web.WebTool;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequestMapping("/api/resource")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ResourceController {

    private final ResourceLoaderTool resourceTool;

    public ResourceController(final ResourceLoaderTool resourceTool) {
        this.resourceTool = resourceTool;
    }

    @GetMapping("/**")
    public @ResponseBody
    byte[] getFileByPath(final HttpServletRequest request) throws IOException {
        final String extractedPath = WebTool.extractUriPath(request);
        final Resource resource = resourceTool.loadResourceByPath(extractedPath);
        return IOUtils.toByteArray(FileUtils.openInputStream(resource.getFile()));
    }
}
