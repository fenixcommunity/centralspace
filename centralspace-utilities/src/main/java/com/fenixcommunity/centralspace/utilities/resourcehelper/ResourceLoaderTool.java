package com.fenixcommunity.centralspace.utilities.resourcehelper;

import com.fenixcommunity.centralspace.utilities.configuration.properties.ResourceProperties;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;
import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;


@Component
//todo @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) add to other
public class ResourceLoaderTool {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ResourceProperties resourceProperties;

    private final Validator validator;

    public ResourceLoaderTool(ValidatorFactory validatorFactory) {
        this.validator = validatorFactory.getInstance(NOT_NULL);
    }

    public Resource loadResourceFile(InternalResource resource) {
        validator.validateWithException(resource);

        String fileParentPath = resolveResourcePrefix(resource);
        StringBuilder fullPath = new StringBuilder("classpath:static/")
                .append(fileParentPath)
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

    private String resolveResourcePrefix(InternalResource resource) {
        var fileFormat = resource.getFileFormat();
            return fileFormat.getSubtype();
    }

}
