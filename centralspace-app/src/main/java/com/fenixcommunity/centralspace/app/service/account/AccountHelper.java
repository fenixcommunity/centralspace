package com.fenixcommunity.centralspace.app.service.account;

import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.mapper.account.modelmapper.AccountModelMapper;
import com.fenixcommunity.centralspace.app.service.account.validator.ValidMappedAccount;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.utilities.common.OperationLevel;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class AccountHelper {

    @ValidMappedAccount // test it good
    public Account mapFromDtoAndValidate(@NonNull final AccountDto accountDto, final OperationLevel level) {
        return new AccountModelMapper(level).mapFromDto(accountDto);
    }
}
