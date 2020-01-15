package com.fenixcommunity.centralspace.app.rest.dto.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fenixcommunity.centralspace.utilities.web.browser.BrowserType;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

//TODO to działa?
@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@Builder
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestCookies {
    private final String browserInfo;

    @JsonIgnore
    public BrowserType getBrowserType() {
        return (isEmpty(browserInfo)) ?
                BrowserType.identifyBrowser(browserInfo) : BrowserType.UNKNOWN;
    }
}
