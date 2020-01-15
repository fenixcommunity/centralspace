package com.fenixcommunity.centralspace.app.rest.dto.register;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fenixcommunity.centralspace.app.rest.dto.config.RestCookies;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;

// JsonIgnoreProperties - REST a,b,c to JAVA a,c
// JsonInclude - JAVA a,b=null,c to REST a,c
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "registerType",
        defaultImpl = RegisterType.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RestRegisterProcess.StandardRegister.class, name = "STANDARD"),
        @JsonSubTypes.Type(value = RestRegisterProcess.AdminRegister.class, name = "ADMIN")})
@FieldDefaults(level = PACKAGE)
public abstract class RestRegisterProcess {

    public RegisterType registerType;
    public RestFilledRegisterForm filledRegisterForm;
    public RestCookies cookies;

    public static class StandardRegister extends RestRegisterProcess {
        public Long userHeadAccountId;
    }

    public static class AdminRegister extends RestRegisterProcess {
        public Long adminCode;
    }

    //TODO tutaj generator kodów
    // todo dodaj exception/handler (np jak enum sie nie zgadza)
}
