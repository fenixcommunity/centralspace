package com.fenixcommunity.centralspace.app.rest.dto.register;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static lombok.AccessLevel.PRIVATE;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@Builder
@FieldDefaults(level = PRIVATE, makeFinal = true)
class RestFilledRegisterForm {

    @NotNull
    private final String firstName;
    private final String lastName;
    private final String mail;
    //TODO opakuj EMAIL
    private final String registerURL;

    //todo test
    //todo @JsonCreator
    //todo jaka data?
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private final ZonedDateTime dateTime;
}
