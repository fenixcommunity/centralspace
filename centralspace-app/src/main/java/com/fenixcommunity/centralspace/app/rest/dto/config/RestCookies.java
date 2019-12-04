package com.fenixcommunity.centralspace.app.rest.dto.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fenixcommunity.centralspace.utilities.web.browser.BrowserType;
import org.springframework.hateoas.RepresentationModel;

import static org.apache.commons.lang3.StringUtils.isEmpty;

//TODO to działa?
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestCookies extends RepresentationModel {
    public String browserInfo;

    @JsonIgnore
    public BrowserType getBrowserType() {
        return (isEmpty(browserInfo)) ?
                BrowserType.identifyBrowser(browserInfo) : BrowserType.UNKNOWN;
    }
}
