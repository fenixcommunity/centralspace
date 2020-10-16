package com.fenixcommunity.centralspace.domain.repository.permanent.account;

import java.util.List;
import java.util.Set;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;

public interface AccountCustomRepository {
    List<Account> findAccountsByEmails(final Set<String> emails);
}

