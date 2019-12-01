package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceLoaderTool resourceTool;

    @GetMapping("/**")
    public @ResponseBody
    byte[] getFileByPath(HttpServletRequest request) throws IOException {
        String extractedPath = extractPath(request);
        Resource resource = resourceTool.loadResourceByFullName(extractedPath);
        return IOUtils.toByteArray(FileUtils.openInputStream(resource.getFile()));
    }

    private String extractPath(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String matchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(matchPattern, path);
    }
}
