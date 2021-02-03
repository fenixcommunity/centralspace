package com.fenixcommunity.centralspace.app.rest.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fenixcommunity.centralspace.app.configuration.security.SecurityUserGroup;
import com.fenixcommunity.centralspace.app.rest.dto.config.RequestInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// JsonIgnoreProperties - REST a,b,c to JAVA a,c
// JsonInclude - JAVA a,b=null,c to REST a,c
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "registerType",
        defaultImpl = RegisterType.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegistrationRestRequest.StandardRegisterRegistration.class, name = "STANDARD"),
        @JsonSubTypes.Type(value = RegistrationRestRequest.AdminRegisterRegistration.class, name = "ADMIN")})
// basic json response annotations + public + @JsonProperty
@NoArgsConstructor @AllArgsConstructor
public abstract class RegistrationRestRequest {
    @JsonProperty
    @ApiModelProperty(example = "STANDARD")
    public RegisterType registerType;
    @JsonProperty
    public RestFilledRegisterForm filledRegisterForm;
    @JsonProperty
    public RequestInfo requestInfo;

    public static class StandardRegisterRegistration extends RegistrationRestRequest {
        public final SecurityUserGroup securityUserGroup = SecurityUserGroup.BASIC_USER;
        public Long userHeadAccountId;
    }

    public static class AdminRegisterRegistration extends RegistrationRestRequest {
        public final SecurityUserGroup securityUserGroup = SecurityUserGroup.ADMIN_USER;
    }
}
