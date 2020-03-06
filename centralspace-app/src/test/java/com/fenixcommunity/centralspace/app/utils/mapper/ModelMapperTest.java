package com.fenixcommunity.centralspace.app.utils.mapper;

import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.mapper.AccountMapperOld;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Address;
import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
import com.fenixcommunity.centralspace.domain.model.mounted.password.PasswordType;
import com.fenixcommunity.centralspace.utilities.common.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.ValidationException;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class ModelMapperTest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime dateTime;

    private ModelMapper modelMapper;
    private Account initAccount;
    private AccountDto initAccountDto;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        initAccount = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL)
                .build();
        List<Password> passwords = singletonList(new Password(ID, initAccount, PASSWORD, PasswordType.TO_CENTRALSPACE));
        initAccount.setPasswords(passwords);
        Address address = new Address(ID, "Poland", "Cracow", singletonList(initAccount));
        initAccount.setAddress(address);

        initAccountDto = AccountMapperOld.mapToDto(initAccount, Level.HIGH);
    }

//    @Test
//    void basicMappingTest() {
//        AccountMapper accountMapper = new AccountMapper();
//        AccountDto accountDto =  accountMapper.map(initAccount);
//        assertEquals(ID, accountDto.getId());
//        assertEquals(LOGIN, accountDto.getLogin());
//        assertEquals(MAIL, accountDto.getMail());
//    }

//    https://stackoverflow.com/questions/46530323/java-object-mapping-framework-working-with-builder-pattern
//    http://modelmapper.org/getting-started/
//    https://stackoverflow.com/questions/44534172/how-to-customize-modelmapper

    @Test
    void test1() {
        AccountDtoWithoutBuilder resultAccountDto = modelMapper.map(initAccount, AccountDtoWithoutBuilder.class);
        ContactDetailsDto resultContactDetailsDto = modelMapper.map(initAccount.getAddress(), ContactDetailsDto.class);
        assertEquals(ID, resultAccountDto.getId());
        assertEquals(LOGIN, resultAccountDto.getLogin());
        assertEquals(MAIL, resultAccountDto.getMail());
        assertEquals("Poland", resultContactDetailsDto.getCountry());
    }

    @Test
    void test2() {
        TypeMap<Account, AccountDtoWithoutBuilder> typeMap = modelMapper.createTypeMap(Account.class, AccountDtoWithoutBuilder.class);

        Converter<List<Password>, String> firstPasswordConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().get(0)
                .getPasswordType().toString().toLowerCase() + "_converter";

        typeMap.addMappings(mapper -> {
            mapper.using(firstPasswordConverter)
                    .map(Account::getPasswords, AccountDtoWithoutBuilder::setPasswordType);
            mapper.using(ctx -> ctx.getSource() == null ? null : ctx.getSource() + "_converter")
                    .map(Account::getLogin, AccountDtoWithoutBuilder::setLogin);
            mapper.skip(AccountDtoWithoutBuilder::setContactDetailsDto);
            mapper.map(Account::getId, AccountDtoWithoutBuilder::setIdString);
        });

        AccountDtoWithoutBuilder resultAccountDto = modelMapper.map(initAccount, AccountDtoWithoutBuilder.class);

        assertEquals(ID, resultAccountDto.getId());
        assertEquals(ID.toString(), resultAccountDto.getIdString());
        assertEquals(MAIL, resultAccountDto.getMail());
        assertEquals(LOGIN + "_converter", resultAccountDto.getLogin());
        assertEquals(PasswordType.TO_CENTRALSPACE.toString().toLowerCase() + "_converter", resultAccountDto.getPasswordType());
    }

    @Test
    void test3() {
        TypeMap<Account, AccountDtoWithoutBuilder> typeMap = modelMapper.createTypeMap(Account.class, AccountDtoWithoutBuilder.class);

        typeMap.addMapping(src -> src.getAddress().getCountry(), (dest, v) -> dest.getContactDetailsDto().setCountry((String) v));

        AccountDtoWithoutBuilder resultAccountDto = modelMapper.map(initAccount, AccountDtoWithoutBuilder.class);

        assertNotNull(resultAccountDto.getContactDetailsDto());
        assertEquals("Poland", resultAccountDto.getContactDetailsDto().getCountry());
    }

    @Test
    void test4() {
        TypeMap<Account, AccountDtoWithoutBuilder> typeMap = modelMapper.createTypeMap(Account.class, AccountDtoWithoutBuilder.class);

        Condition notNull = ctx -> ctx.getSource() != null;
        Converter<List<Password>, String> firstPasswordConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().get(0)
                .getPasswordType().toString().toLowerCase() + "_converter";

        typeMap.addMappings(mapper -> {
            mapper.when(notNull)
                    .using(firstPasswordConverter)
                    .map(Account::getPasswords, AccountDtoWithoutBuilder::setPasswordType);
            mapper.when(ctx -> {
                Long accountId = (Long) ctx.getSource();
                return accountId == 1L;
            }).map(Account::getId, AccountDtoWithoutBuilder::setMail);
        });

        AccountDtoWithoutBuilder resultAccountDto = modelMapper.map(initAccount, AccountDtoWithoutBuilder.class);

        assertEquals(ID.toString(), resultAccountDto.getMail());
        assertEquals(PasswordType.TO_CENTRALSPACE.toString().toLowerCase() + "_converter", resultAccountDto.getPasswordType());
    }

    @Test
    void test5() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            ContactDetailsDto resultContactDetailsDto = modelMapper.map(initAccount.getAddress(), ContactDetailsDto.class);
            modelMapper.validate();
    });
        ValidationException validationException = (ValidationException) exception;
    }

    @Test
    void test6() {
     //todo test for builder
        todo i przerób
    }
}
