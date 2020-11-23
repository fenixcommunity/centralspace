package com.fenixcommunity.centralspace.app.rest.dto.resource;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data @FieldDefaults(level = PRIVATE)
public final class ExternalResourceDto {
    private String urlPath;
    private String destinationFilePath;

    @JsonCreator
    @Builder
    public ExternalResourceDto(@JsonProperty("urlPath") String urlPath, @JsonProperty("destinationFilePath") String destinationFilePath) {
        this.urlPath = urlPath;
        this.destinationFilePath = destinationFilePath;
    }
}