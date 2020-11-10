package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Locale;

import com.fenixcommunity.centralspace.app.service.customization.CustomizationService;
import com.fenixcommunity.centralspace.utilities.translator.AppLocale;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController @RequestMapping("/api/customization")
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true) @AllArgsConstructor(access = PACKAGE)
public class CustomizationController {

    private final CustomizationService customizationService;

    @PostMapping("/switch-user-locale")
    public Mono<String> getInfo(@RequestParam(value = "locale", defaultValue = "PL") final AppLocale locale,
                                @RequestHeader(value = "accept-language", required = false, defaultValue = "pl-PL") @ApiIgnore String language) {
        final Locale switchedLocale = customizationService.switchUserLocale(locale);

        return Mono.just(switchedLocale.toLanguageTag());
    }
}
