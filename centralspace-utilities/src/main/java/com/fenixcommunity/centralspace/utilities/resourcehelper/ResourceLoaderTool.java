package com.fenixcommunity.centralspace.utilities.resourcehelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResourceLoaderTool {

    @Autowired
    private ResourceLoader resourceLoader;

    public Resource loadResourceFile(InternalResource resource) {
        //todo validator
        String subtype = resource.getFileType().getSubtype();
        StringBuilder fullPath = new StringBuilder("classpath:static/")
                .append(subtype)
                .append(SLASH)
                .append(resource.getFullName());
        return resourceLoader.getResource(fullPath.toString());
    }

    public Resource loadResourceByPath(String filePath) {
        return resourceLoader.getResource("classpath:" + filePath);
    }
}
