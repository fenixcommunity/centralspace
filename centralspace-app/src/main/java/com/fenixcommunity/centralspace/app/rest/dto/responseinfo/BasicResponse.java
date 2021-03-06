package com.fenixcommunity.centralspace.app.rest.dto.responseinfo;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@ApiModel(value = "RestResponse")
@Value @Builder @FieldDefaults(level = PRIVATE, makeFinal = true)
public class BasicResponse {

    @ApiModelProperty(value = "Basic description", required = true, example = "INSERT", allowableValues = "INSERT,DELETE,UPDATE")
    private final String description;

    @ApiModelProperty(value = "Status of this ExtractionRequest", required = true, example = "NEW", allowableValues = "NEW,ERROR,IN_PROCESS,PROCESSED")
    private final String status;
}