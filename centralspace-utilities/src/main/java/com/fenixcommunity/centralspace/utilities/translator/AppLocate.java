package com.fenixcommunity.centralspace.utilities.translator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum AppLocate {
    US("en-US"),
    PL("pl-PL");

    private final String tag;
}
