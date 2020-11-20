package com.fenixcommunity.centralspace.app.service.customization;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

import com.fenixcommunity.centralspace.utilities.translator.AppLabel;
import com.fenixcommunity.centralspace.utilities.translator.AppLocale;
import com.fenixcommunity.centralspace.utilities.translator.TranslationService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomizationService {
    private final UserCustomizationProvider userCustomizationProvider;
    private final TranslationService translationService;
    @Qualifier
    private Set<String> allowedLocaleLanguages;

    public String translate(final AppLabel label, final Object... formatArguments) {
        final Locale userLocale = userCustomizationProvider.getUserLocale();
        return translationService.getMessage(label, userLocale, formatArguments);
    }

    public Locale switchUserLocale(final AppLocale appLocale) {
        if (appLocale != null && allowedLocaleLanguages.contains(appLocale.getLanguage())) {
            Locale userLocale = new Locale.Builder()
                    .setLanguage(appLocale.getLanguage())
                    .setRegion(appLocale.getRegion())
                    .build();
            userCustomizationProvider.setUserLocale(userLocale);
        }
        return userCustomizationProvider.getUserLocale();
    }

    public NumberFormat switchUserNumberFormat(final NumberFormat providedNumberFormat) {
        if (providedNumberFormat != null) {
            userCustomizationProvider.setUserNumberFormat(providedNumberFormat);
        }

        return userCustomizationProvider.getUserNumberFormat();
    }

    public DateTimeFormatter switchUserDateFormat(final DateTimeFormatter providedDateFormat) {
        if (providedDateFormat != null) {
            userCustomizationProvider.setUserDateFormat(providedDateFormat);
        }
        return userCustomizationProvider.getUserDateFormat();
    }
}
