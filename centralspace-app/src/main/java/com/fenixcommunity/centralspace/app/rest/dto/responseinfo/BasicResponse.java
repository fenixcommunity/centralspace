package com.fenixcommunity.centralspace.app.rest.dto.responseinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@ApiModel(value = "RestResponse")
public class BasicResponse {

    @ApiModelProperty(value = "Basic description", required = true, example = "INSERT", allowableValues = "INSERT,DELETE,UPDATE")
    private String description;

    @ApiModelProperty(value = "Status of this ExtractionRequest", required = true, example = "NEW", allowableValues = "NEW,ERROR,IN_PROCESS,PROCESSED")
    private String status;
}