package com.fenixcommunity.centralspace.app.rest.dto.config;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fenixcommunity.centralspace.utilities.web.browser.BrowserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Getter @NoArgsConstructor @AllArgsConstructor @FieldDefaults(level = PRIVATE)
public class RequestInfo {
    @JsonProperty
    @ApiModelProperty(example = "firefox")
    private String browserInfo;

    @JsonIgnore
    public BrowserType getBrowserType() {
        return isEmpty(browserInfo) ?
                BrowserType.identifyBrowser(browserInfo) : BrowserType.UNKNOWN;
    }
}
