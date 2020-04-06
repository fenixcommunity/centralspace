package com.fenixcommunity.centralspace.app.service.resource;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.io.FileNotFoundException;

import com.fenixcommunity.centralspace.app.rest.dto.aws.InternalResourceDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class ResourceService {

    private final ResourceLoaderTool resourceTool;

    //todo @Cacheable to store files
    public Resource getInternalResource(final String extractedPath) {
        return resourceTool.loadResourceByPath(extractedPath);
    }

    public boolean isInternalResourceExists(final String extractedPath) {
        try {
            return resourceTool.loadResourceByPath(extractedPath).getFile().exists();
        } catch (FileNotFoundException e) {
            log.warn("Problem with finding the file");
            return false;
        } catch (Exception e) {
            throw new ServiceFailedException("Problem during finding resource");
        }
    }

    public String getInternalImagePath(final InternalResourceDto internalResourceDto) {
        return resourceTool.getResourceProperties().getImageUrl()
                + internalResourceDto.getFileName() + DOT + internalResourceDto.getFileSubType();
    }
}