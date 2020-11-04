package com.fenixcommunity.centralspace.app.service.customization;

import static lombok.AccessLevel.PRIVATE;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.annotation.PostConstruct;

import com.fenixcommunity.centralspace.app.configuration.web.LocaleProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component @SessionScope
@FieldDefaults(level = PRIVATE, makeFinal = false)
@Getter @Setter
public class UserCustomizationProvider {
    public static final String BASIC_DATE_TIME_PATTERN = "dd-MMMM-yyyy HH:mm:ss.SSS";

    private Locale userLocale;
    private DateTimeFormatter userDateFormat;
    private NumberFormat userNumberFormat;

    private LocaleProperties localeProperties;

    @Autowired
    public UserCustomizationProvider(LocaleProperties localeProperties) {
        this.localeProperties = localeProperties;
    }

    @PostConstruct
    public void initUserCustomizationProvider() {
        userLocale = initDefaultLocale();
        userDateFormat = initUserDateFormat();
        userNumberFormat = initUserNumberFormat();
    }

    public Locale initDefaultLocale() {
        return localeProperties == null
                ? Locale.getDefault()
                : new Locale.Builder()
                .setLanguage(localeProperties.getLanguage())
                .setRegion(localeProperties.getRegion())
                .build();
    }

    public NumberFormat initUserNumberFormat() {
        return NumberFormat.getCurrencyInstance(userLocale);
    }

    public DateTimeFormatter initUserDateFormat() {
        return DateTimeFormatter.ofPattern(BASIC_DATE_TIME_PATTERN, userLocale);
    }
}
