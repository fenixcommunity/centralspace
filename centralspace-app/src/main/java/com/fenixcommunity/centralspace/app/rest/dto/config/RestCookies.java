package com.fenixcommunity.centralspace.app.rest.dto.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fenixcommunity.centralspace.app.utils.browser.BrowserType;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestCookies extends ResourceSupport {
   public String browserInfo;

    @JsonIgnore
    public BrowserType getBrowserType() {
        return (!StringUtils.isEmpty(browserInfo)) ?
                BrowserType.identifyBrowser(browserInfo) : BrowserType.UNKNOWN;
    }
}
