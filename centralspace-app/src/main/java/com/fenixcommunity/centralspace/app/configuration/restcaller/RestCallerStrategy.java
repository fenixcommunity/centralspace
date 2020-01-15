package com.fenixcommunity.centralspace.app.configuration.restcaller;

import com.fenixcommunity.centralspace.app.configuration.restcaller.webclient.RestCallerType;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestCallerStrategy {
    private final RestCallerType restCallerType;

}
