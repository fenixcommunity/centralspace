package com.fenixcommunity.centralspace.domain.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE) @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Encryption {
    private String algorithm;
    private String key;
}