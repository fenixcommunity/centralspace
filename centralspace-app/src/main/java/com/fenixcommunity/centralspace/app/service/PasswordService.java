package com.fenixcommunity.centralspace.app.service;

import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
import com.fenixcommunity.centralspace.domain.repository.mounted.PasswordRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PasswordService {

    private final PasswordRepository passwordRepository;

    @Autowired
    public PasswordService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public Password save(Password password) {
        return passwordRepository.save(password);
    }

    public List<Password> findAll() {
        return (List<Password>) passwordRepository.findAll();
    }
}
