package com.fenixcommunity.centralspace.app.service;

import com.fenixcommunity.centralspace.app.configuration.security.AuthenticationFacade;
import com.fenixcommunity.centralspace.app.configuration.security.SecurityRole;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.NOT_NULL;
import static org.apache.commons.lang3.EnumUtils.isValidEnum;

@Service
public class SecurityService {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    private final Validator validator;

    @Autowired
    public SecurityService(ValidatorFactory validatorFactory) {
        this.validator = validatorFactory.getInstance(NOT_NULL);
    }

    @PostConstruct
    public void initComponent() {
        validator.validateWithException(authenticationFacade);
    }

    public boolean isValidSecurityRole() {
        var authentication = authenticationFacade.getAuthentication();
        validator.validateAllWithException(authentication, authentication.getName());
        var role = authentication.getName();
        return isValidEnum(SecurityRole.class, role);
    }
}