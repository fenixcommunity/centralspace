package com.fenixcommunity.centralspace.app.rest.mapper;

import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.domain.model.account.Account;
import com.fenixcommunity.centralspace.utilities.common.Level;

import static java.util.Objects.isNull;

public class AccountMapper {

    public static AccountDto mapToDto(Account account, Level level) {
        AccountDto rest = new AccountDto();
        if (Level.HIGH.equals(level)) {
            if (!isNull(account.getId())) {
                rest.accountId = account.getId().toString();
            }
        }
        rest.login = account.getLogin();
        rest.email = account.getEmail();

        return rest;
    }

    public static Account mapToJpa(AccountDto rest) {
        return Account.builder()
                .login(rest.login)
                .email(rest.email).build();
    }
}
