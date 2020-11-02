package com.fenixcommunity.centralspace.app.service.customization;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.utilities.translator.AppLabel;
import com.fenixcommunity.centralspace.utilities.translator.AppLocate;
import com.fenixcommunity.centralspace.utilities.translator.TranslationService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomizationService {

    private final TranslationService translationService;

    public String translate(final AppLabel label, final AppLocate locate, final Object... formatArguments) {
        //todo set user Locate
        return translationService.getMessage(label, locate, formatArguments);
    }
}
