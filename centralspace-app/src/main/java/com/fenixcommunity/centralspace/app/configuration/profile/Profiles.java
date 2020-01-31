package com.fenixcommunity.centralspace.app.configuration.profile;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Profiles {

    public static final String STANDALONE_PROFILE = "STANDALONE_PROFILE";

    public static final String SWAGGER_ENABLED_PROFILE = "SWAGGER_ENABLED_PROFILE";

    public static final String TEST_PROFILE = "TEST_PROFILE";
}