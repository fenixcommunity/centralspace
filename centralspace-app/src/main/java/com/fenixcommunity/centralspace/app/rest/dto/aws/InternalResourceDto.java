package com.fenixcommunity.centralspace.app.rest.dto.aws;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data @FieldDefaults(level = PRIVATE)
public final class InternalResourceDto {
    private String fileName;
    private FileFormat fileFormat;

    @JsonCreator
    @Builder
    public InternalResourceDto(@JsonProperty("fileName") String fileName, @JsonProperty("fileFormat") FileFormat fileFormat) {
        this.fileName = fileName;
        this.fileFormat = fileFormat;
    }
}