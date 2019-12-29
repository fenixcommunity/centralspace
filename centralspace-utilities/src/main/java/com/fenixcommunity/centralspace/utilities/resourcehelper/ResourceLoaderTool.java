package com.fenixcommunity.centralspace.utilities.resourcehelper;

import com.fenixcommunity.centralspace.utilities.configuration.properties.ResourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;


@Component
//todo @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) add to other
public class ResourceLoaderTool {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ResourceProperties resourceProperties;

    public Resource loadResourceFile(InternalResource resource) {
        //todo validator
        String subtype = resource.getFileFormat().getSubtype();
        StringBuilder fullPath = new StringBuilder("classpath:static/")
                .append(subtype)
                .append(SLASH)
                .append(resource.getFullName());
        return resourceLoader.getResource(fullPath.toString());
    }

    public Resource loadResourceByPath(String filePath) {
        return resourceLoader.getResource("classpath:" + filePath);
    }

    public ResourceProperties getResourceProperties() {
        return resourceProperties;
    }
}
