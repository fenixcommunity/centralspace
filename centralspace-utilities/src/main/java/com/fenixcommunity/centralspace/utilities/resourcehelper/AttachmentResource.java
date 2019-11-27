package com.fenixcommunity.centralspace.utilities.resourcehelper;

import org.springframework.http.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public final class AttachmentResource {
    private String fileName;
    private MediaType fileType;
}
