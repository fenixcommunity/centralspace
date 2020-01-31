package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.fenixcommunity.centralspace.utilities.web.WebTool;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller @RequestMapping("/api/resource")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class ResourceController {

    private final ResourceLoaderTool resourceTool;

    @GetMapping("/**")
    public @ResponseBody
    byte[] getFileByPath(final HttpServletRequest request) throws IOException {
        final String extractedPath = WebTool.extractUriPath(request);
        final Resource resource = resourceTool.loadResourceByPath(extractedPath);
        return IOUtils.toByteArray(FileUtils.openInputStream(resource.getFile()));
    }
}
