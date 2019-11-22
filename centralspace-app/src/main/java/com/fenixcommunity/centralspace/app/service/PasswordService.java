package com.fenixcommunity.centralspace.app.service;

import com.fenixcommunity.centralspace.domain.model.password.Password;
import com.fenixcommunity.centralspace.domain.repository.PasswordRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
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
