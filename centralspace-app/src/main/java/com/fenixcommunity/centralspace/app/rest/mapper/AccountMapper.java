package com.fenixcommunity.centralspace.app.rest.mapper;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto.AccountDtoBuilder;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.utilities.common.Level;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountMapper {

    public static AccountDto mapToDto(final Account account, final Level level) {
        final AccountDtoBuilder dtoBuilder = AccountDto.builder();
        if (Level.HIGH.equals(level)) {
            if (!isNull(account.getId())) {
                dtoBuilder.id(account.getId());
            }
        }
        dtoBuilder.login(account.getLogin());
        dtoBuilder.mail(account.getMail());
        return dtoBuilder.build();
    }

    public static Account mapToJpa(AccountDto rest) {
        return Account.builder()
                .login(rest.getLogin())
                .mail(rest.getMail()).build();
    }
}
