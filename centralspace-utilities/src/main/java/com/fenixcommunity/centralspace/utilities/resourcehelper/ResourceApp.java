package com.fenixcommunity.centralspace.utilities.resourcehelper;

import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;

@Getter
public final class ResourceApp {
    private String fileName;
    private MediaType fileType;
    private Resource content;
    //todo more than like instead extend class


    private ResourceApp() {
    }

    public ResourceApp resourceByFullName(String fullName) {
        ResourceApp resource = new ResourceApp();
        String[] splitedName = fullName.split(".");
        resource.fileName = splitedName[0];
        resource.fileType = MediaType.parseMediaType(splitedName[1]);
        return resource;
    }

    public static ResourceApp resourceByNameAndType(String fileName, MediaType fileType) {
        ResourceApp resource = new ResourceApp();
        resource.fileName = fileName;
        resource.fileType = fileType;
        return resource;
    }

    public String getFullName() {
        return fileName + DOT + fileType.getSubtype();
    }

    public void setContent(Resource content) {
        if (this.content == null) {
            this.content = content;
        }
    }

    public boolean fileExists() {
        return this.content != null;
    }
}
