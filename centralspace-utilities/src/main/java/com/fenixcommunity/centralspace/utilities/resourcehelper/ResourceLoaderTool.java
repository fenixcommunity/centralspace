package com.fenixcommunity.centralspace.utilities.resourcehelper;

import com.google.common.net.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;


@Component
public class ResourceLoaderTool {

    @Autowired
    private ResourceLoader resourceLoader;

    public Resource loadResourceByName(String fileName, MediaType type) {
        //todo validator
       return resourceLoader.getResource("classpath:"+ fileName + DOT + type.subtype());
    }
}
