package com.fenixcommunity.centralspace.app.service;

import com.fenixcommunity.centralspace.domain.model.account.Account;
import com.fenixcommunity.centralspace.domain.repository.AccountRepository;
import com.fenixcommunity.centralspace.utilities.aop.AppMonitoring;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    public Optional<Account> findById(Long id) {
        //TODO of null co zrobic?
        return accountRepository.findById(id);
    }

    @AppMonitoring
    public List<Account> findAll() {
        return (List<Account>) accountRepository.findAll();
    }
}
