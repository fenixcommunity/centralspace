package com.fenixcommunity.centralspace.app.service.password;

import static com.fenixcommunity.centralspace.utilities.validator.ValidatorType.PASSWORD_CUSTOM;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import com.fenixcommunity.centralspace.domain.model.permanent.password.PasswordType;
import com.fenixcommunity.centralspace.domain.repository.permanent.PasswordRepository;
import com.fenixcommunity.centralspace.utilities.validator.Validator;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorFactory;
import com.fenixcommunity.centralspace.utilities.validator.ValidatorType;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PasswordService {
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public PasswordService(PasswordRepository passwordRepository, PasswordEncoder passwordEncoder, ValidatorFactory validator) {
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator.getInstance(PASSWORD_CUSTOM);
    }

    public Password generatePassword(final char[] providedPassword,
                                     @NonNull final Account account) {
        if (!passwordIsValid(providedPassword)) {
            return null;
        }

        final Password password = Password.builder()
                .account(account)
                .password(passwordEncoder.encode(new String(providedPassword)).toCharArray())
                .passwordType(PasswordType.TO_CENTRALSPACE)
                .build();
        return passwordRepository.save(password);
    }

    public boolean passwordIsValid(final char[] providedPassword) {
        return validator.isValid(providedPassword);
    }
}
