package com.fenixcommunity.centralspace.utilities.resourcehelper;

import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import lombok.Getter;
import org.springframework.core.io.Resource;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;

@Getter
public final class InternalResource {
    private String fileName;
    private FileFormat fileFormat;
    private Resource content;
    //todo more than like instead extend class


    public static InternalResource resourceByFullName(String fullName) {
        InternalResource resource = new InternalResource();
        String[] splitedName = fullName.split("\\.");
        resource.fileName = splitedName[0];
        if (splitedName.length > 1) {
            resource.fileFormat = FileFormat.parseFileFormat(splitedName[1]);
        }
        return resource;
    }

    public static InternalResource resourceByNameAndType(String fileName, FileFormat fileFormat) {
        InternalResource resource = new InternalResource();
        resource.fileName = fileName;
        resource.fileFormat = fileFormat;
        return resource;
    }

    public String getFullName() {
        return fileName + DOT + fileFormat.getSubtype();
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
