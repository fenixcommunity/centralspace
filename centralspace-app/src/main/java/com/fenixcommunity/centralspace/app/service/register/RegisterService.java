package com.fenixcommunity.centralspace.app.service.register;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.service.security.user.CreateUserData;
import com.fenixcommunity.centralspace.app.service.security.user.SecuredUserCreator;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RegisterService {
    private final ApplicationEventPublisher eventPublisher;
    private final SecuredUserCreator securedUserCreator;

    public void registerNewAccount(final CreateUserData createUserData) {
        securedUserCreator.createSecuredUser(createUserData);
//TODO     tutaj serwis z obserwatorem wysylajacego maila do administracji. Globalny serwis informujacy o zmianie
//TODO     eventPublisher.publishEvent(new OnRegistrationCompleteEvent(new User("userName", "password", emptyList()), request.getLocale(), appUrl));
//TODO     Publisher -> user ktory updatuje, tworzy + EntityListenet . Publisher = Account i inne informacje, np komentarz
    }
}
