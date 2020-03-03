package com.fenixcommunity.centralspace.app.utils;

import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.utilities.common.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

public class ModelMapperTest {

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private ZonedDateTime dateTime;

    private ModelMapper modelMapper = new ModelMapper();
    private Account initAccount;
    private AccountDto initAccountDto;

    @BeforeEach
    public void before() {
        initAccount = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL)
                .passwords(Collections.singletonList(null))
                .build();
        initAccountDto = AccountMapper.mapToDto(initAccount, Level.HIGH);
    }

    @Test
    void basicMappingTest() {
        AccountDto accountDto = modelMapper.map(initAccount, AccountDto.class);
        assertEquals(ID, accountDto.getId());
        assertEquals(LOGIN, accountDto.getLogin());
        assertEquals(MAIL, accountDto.getMail());
        todo
    }
}
