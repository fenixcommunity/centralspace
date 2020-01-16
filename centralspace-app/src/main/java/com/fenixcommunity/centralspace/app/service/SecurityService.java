package com.fenixcommunity.centralspace.app.service;

import com.fenixcommunity.centralspace.app.configuration.security.autosecurity.AuthenticationFacade;
import com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.EnumUtils.isValidEnum;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class SecurityService {

    private final AuthenticationFacade authenticationFacade;
    private final Validator validator;

    @Autowired
    SecurityService(AuthenticationFacade authenticationFacade, ValidatorFactory validatorFactory) {
        this.authenticationFacade = authenticationFacade;
        this.validator = validatorFactory.getInstance(NOT_NULL);
    }

    @PostConstruct
    public void initComponent() {
        validator.validateWithException(authenticationFacade);
    }

    public boolean isValidSecurityRole() {
        final var authentication = authenticationFacade.getAuthentication();
        validator.validateAllWithException(authentication, authentication.getName());
        final var role = authentication.getName();
        return isValidEnum(SecurityRole.class, role);
    }
}