package com.fenixcommunity.centralspace.app.utils.mapper;

import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.mapper.AccountMapperOld;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
import com.fenixcommunity.centralspace.domain.model.mounted.password.PasswordType;
import com.fenixcommunity.centralspace.utilities.common.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class ModelMapperTest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime dateTime;

    private ModelMapper modelMapper;
    private Account initAccount;
    private AccountDto initAccountDto;

    @BeforeEach
    public void before() {
        initAccount = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL)
                .build();
        List<Password> passwords = singletonList(new Password(ID, initAccount, PASSWORD, PasswordType.TO_CENTRALSPACE));
        initAccount.setPasswords(passwords);

        initAccountDto = AccountMapperOld.mapToDto(initAccount, Level.HIGH);
    }

    @Test
    void basicMappingTest() {
        AccountMapper accountMapper = new AccountMapper();
        AccountDto accountDto =  accountMapper.map(initAccount);
        assertEquals(ID, accountDto.getId());
        assertEquals(LOGIN, accountDto.getLogin());
        assertEquals(MAIL, accountDto.getMail());
    }
    https://stackoverflow.com/questions/46530323/java-object-mapping-framework-working-with-builder-pattern
    http://modelmapper.org/getting-started/
}
