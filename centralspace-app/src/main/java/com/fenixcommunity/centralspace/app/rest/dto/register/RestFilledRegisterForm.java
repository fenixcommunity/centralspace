package com.fenixcommunity.centralspace.app.rest.dto.register;

import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value @Builder @FieldDefaults(level = PRIVATE, makeFinal = true)
class RestFilledRegisterForm {

    @NotNull
    private final String firstName;
    private final String lastName;
    private final String mail;
    //TODO opakuj EMAIL
    private final String registerURL;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private final ZonedDateTime dateTime;
}
