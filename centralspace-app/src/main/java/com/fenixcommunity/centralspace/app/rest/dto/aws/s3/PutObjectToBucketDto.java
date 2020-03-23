package com.fenixcommunity.centralspace.app.rest.dto.aws.s3;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fenixcommunity.centralspace.app.rest.dto.aws.InternalResourceDto;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data @FieldDefaults(level = PRIVATE)
public class PutObjectToBucketDto {
    private final String bucketName;
    private final String key;
    private final InternalResourceDto internalResourceDto;

    @JsonCreator
    @Builder
    public PutObjectToBucketDto(@JsonProperty("bucketName") String bucketName,
                                @JsonProperty("key") String key,
                                @JsonProperty("resource") InternalResourceDto internalResourceDto) {
        this.bucketName = bucketName;
        this.key = key;
        this.internalResourceDto = internalResourceDto;
    }
}