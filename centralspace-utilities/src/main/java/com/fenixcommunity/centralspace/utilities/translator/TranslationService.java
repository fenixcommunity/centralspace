package com.fenixcommunity.centralspace.utilities.translator;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class TranslationService {
    public static final String MESSAGES_PROPERTY = "messages";

    //TODO create map with keys?
    public String getMessage(final AppLabel label, final Locale locale, final Object... formatArguments) {
        final var bundle = PropertyResourceBundle.getBundle(MESSAGES_PROPERTY, locale);
        final String rawMessage = bundle.getString(label.getName());

        if (formatArguments.length > 0) {
            return new MessageFormat(rawMessage, locale).format(formatArguments);
        }
        return rawMessage;

    }
}
