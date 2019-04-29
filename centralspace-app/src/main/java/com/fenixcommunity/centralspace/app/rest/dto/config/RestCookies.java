package com.fenixcommunity.centralspace.app.rest.dto.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestCookies extends ResourceSupport {
   public String browserInfo;
}
