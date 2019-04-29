package com.fenixcommunity.centralspace.app.rest.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.hateoas.ResourceSupport;

// JsonIgnoreProperties - REST a,b,c to JAVA a,c
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "registerType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RestFilledRegisterForm.StandardRegister.class, name = "standard"),
        @JsonSubTypes.Type(value = RestFilledRegisterForm.AdminRegister.class, name = "admin")})
public abstract class RestFilledRegisterForm extends ResourceSupport {
    public String firstName;
    public String lastName;
    public String email;
    public String registerURL;
    public RegisterType registerType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StandardRegister extends RestFilledRegisterForm {
        public Long userHeadAccountId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AdminRegister extends RestFilledRegisterForm {
        public Long adminCode;
    }

    //TODO tutaj generator kodów

    //todo Ignore/Include czy aby na pewno?
}
