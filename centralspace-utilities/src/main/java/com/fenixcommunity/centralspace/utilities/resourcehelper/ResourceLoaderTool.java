package com.fenixcommunity.centralspace.utilities.resourcehelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResourceLoaderTool {

    @Autowired
    private ResourceLoader resourceLoader;

    public Resource loadResourceByNameAndType(String fileName, MediaType type) {
        //todo validator
        String subtype = type.getSubtype();
       return resourceLoader.getResource("classpath:" + SLASH + fileName + DOT + subtype);
    }

    public Resource loadResourceByFullName(String fileName) {
        //todo validator
        return resourceLoader.getResource("classpath:"+ fileName);
    }
}
