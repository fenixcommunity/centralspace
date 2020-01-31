package com.fenixcommunity.centralspace.app.service;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
import com.fenixcommunity.centralspace.domain.repository.mounted.PasswordRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class PasswordService {

    private final PasswordRepository passwordRepository;

    public Password save(final Password password) {
        return passwordRepository.save(password);
    }

    public List<Password> findAll() {
        return (List<Password>) passwordRepository.findAll();
    }
}
