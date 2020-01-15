package com.fenixcommunity.centralspace.app.service;

import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
import com.fenixcommunity.centralspace.domain.repository.mounted.PasswordRepository;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PasswordService {

    private final PasswordRepository passwordRepository;

    @Autowired
    public PasswordService(final PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public Password save(final Password password) {
        return passwordRepository.save(password);
    }

    public List<Password> findAll() {
        return (List<Password>) passwordRepository.findAll();
    }
}
