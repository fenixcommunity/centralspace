package com.fenixcommunity.centralspace.app.rest.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fenixcommunity.centralspace.app.rest.dto.config.RestCookies;
import com.fenixcommunity.centralspace.app.utils.browser.BrowserType;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.StringUtils;

// JsonIgnoreProperties - REST a,b,c to JAVA a,c
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestRegisterProcess extends ResourceSupport {
    public RestFilledRegisterForm filledRegisterForm;
    public RestCookies cookies;

    @JsonIgnore
    public BrowserType getBrowserType() {
        return (cookies != null && !StringUtils.isEmpty(cookies.browserInfo)) ?
                BrowserType.identifyBrowser(cookies.browserInfo) : BrowserType.UNKNOWN;
    }
    //todo Ignore/Include czy aby na pewno?
}
