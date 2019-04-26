package com.fenixcommunity.centralspace.app.service;

import com.fenixcommunity.centralspace.domain.model.account.Account;
import com.fenixcommunity.centralspace.domain.repository.AccountRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
