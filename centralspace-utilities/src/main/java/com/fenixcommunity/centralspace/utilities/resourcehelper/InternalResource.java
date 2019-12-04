package com.fenixcommunity.centralspace.utilities.resourcehelper;

import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;

@Getter
public final class InternalResource {
    private String fileName;
    private MediaType fileType;
    private Resource content;
    //todo more than like instead extend class


    private InternalResource() {
    }

    public InternalResource resourceByFullName(String fullName) {
        InternalResource resource = new InternalResource();
        String[] splitedName = fullName.split(".");
        resource.fileName = splitedName[0];
        resource.fileType = MediaType.parseMediaType(splitedName[1]);
        return resource;
    }

    public static InternalResource resourceByNameAndType(String fileName, MediaType fileType) {
        InternalResource resource = new InternalResource();
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
