package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.fenixcommunity.centralspace.utilities.web.WebTool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceLoaderTool resourceTool;

    public ResourceController(ResourceLoaderTool resourceTool) {
        this.resourceTool = resourceTool;
    }

    @GetMapping("/**")
    public @ResponseBody
    byte[] getFileByPath(HttpServletRequest request) throws IOException {
        String extractedPath = WebTool.extractUriPath(request);
        Resource resource = resourceTool.loadResourceByPath(extractedPath);
        return IOUtils.toByteArray(FileUtils.openInputStream(resource.getFile()));
    }
}
