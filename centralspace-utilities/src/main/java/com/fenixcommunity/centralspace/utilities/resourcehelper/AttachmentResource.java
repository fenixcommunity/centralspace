package com.fenixcommunity.centralspace.utilities.resourcehelper;

import com.google.common.net.MediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public final class AttachmentResource {
    private String fileName;
    private MediaType fileType;
}
