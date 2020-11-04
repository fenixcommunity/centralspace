package com.fenixcommunity.centralspace.utilities.translator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum AppLocale {
    US("en", "US"),
    PL("pl", "PL");

    private final String language;
    private final String region;

    public String getLanguageTag() {
        return language + "-" + region;
    }
}
