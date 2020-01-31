package com.fenixcommunity.centralspace.utilities.resourcehelper;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;

import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
public final class InternalResource {
    private String fileName;
    private FileFormat fileFormat;
    private Resource content;
    //todo more than like instead extend class


    public static InternalResource resourceByFullName(final String fullName) {
        final InternalResource resource = new InternalResource();
        final String[] splitedName = fullName.split("\\.");
        resource.fileName = splitedName[0];
        if (splitedName.length > 1) {
            resource.fileFormat = FileFormat.parseFileFormat(splitedName[1]);
        }
        return resource;
    }

    public static InternalResource resourceByNameAndType(final String fileName, final FileFormat fileFormat) {
        final InternalResource resource = new InternalResource();
        resource.fileName = fileName;
        resource.fileFormat = fileFormat;
        return resource;
    }

    public String getFullName() {
        return fileName + DOT + fileFormat.getSubtype();
    }

    public void setContent(final Resource content) {
        if (this.content == null) {
            this.content = content;
        }
    }

    public boolean fileExists() {
        return this.content != null;
    }
}
