package com.fenixcommunity.centralspace.utilities.translator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum AppLabel {
    SENT_MASSAGE("sentMassage"),
    SENT_MESSAGE_WITH_AUTHOR_AND_DATE("sentMessageWithAuthorAndDate");

    private final String name;
}
