package com.fenixcommunity.centralspace.app.helper.mapper;

import static com.fenixcommunity.centralspace.utilities.common.Var.CITY;
import static com.fenixcommunity.centralspace.utilities.common.Var.COUNTRY;
import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto.AccountDtoBuilder;
import com.fenixcommunity.centralspace.app.rest.dto.account.ContactDetailsDto;
import com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper;
import com.fenixcommunity.centralspace.app.rest.mapper.ModelMapperBuilder;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Address;
import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
import com.fenixcommunity.centralspace.domain.model.mounted.password.PasswordType;
import com.fenixcommunity.centralspace.utilities.common.OperationLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;

public class ModelMapperTest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime dateTime;

    private ModelMapper modelMapperBasic;
    private ModelMapper modelMapperForBuilder;
    private Account initAccount;

    @BeforeEach
    public void before() {
        modelMapperBasic = new ModelMapperBuilder()
                .withMatchingStrategy(MatchingStrategies.STANDARD)
                .build();
        modelMapperForBuilder = new ModelMapperBuilder()
                .withUsingLombokBuilder()
                .withMethodAccessLevelToMapping(AccessLevel.PUBLIC)
                .withNameConvention(NameTokenizers.CAMEL_CASE, NameTokenizers.UNDERSCORE)
                .build();

        initAccount = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL)
                .build();
        List<Password> passwords = singletonList(new Password(ID, initAccount, PASSWORD, PasswordType.TO_CENTRALSPACE));
        initAccount.setPasswords(passwords);
        Address address = new Address(ID, COUNTRY, CITY, singletonList(initAccount));
        initAccount.setAddress(address);
    }

    @Test
    void exampleTest1() {
        AccountDtoWithoutBuilder resultAccountDto = modelMapperBasic.map(initAccount, AccountDtoWithoutBuilder.class);
        ContactDetailsDto resultContactDetailsDto = modelMapperBasic.map(initAccount.getAddress(), ContactDetailsDto.class);
        assertEquals(ID, resultAccountDto.getId());
        assertEquals(LOGIN, resultAccountDto.getLogin());
        assertEquals(MAIL, resultAccountDto.getMail());
        assertEquals(COUNTRY, resultContactDetailsDto.getCountry());
    }

    @Test
    void exampleTest2() {
        var typeMap = modelMapperBasic.createTypeMap(Account.class, AccountDtoWithoutBuilder.class);

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

        AccountDtoWithoutBuilder resultAccountDto = modelMapperBasic.map(initAccount, AccountDtoWithoutBuilder.class);

        assertEquals(ID, resultAccountDto.getId());
        assertEquals(ID.toString(), resultAccountDto.getIdString());
        assertEquals(MAIL, resultAccountDto.getMail());
        assertEquals(LOGIN + "_converter", resultAccountDto.getLogin());
        assertEquals(PasswordType.TO_CENTRALSPACE.toString().toLowerCase() + "_converter", resultAccountDto.getPasswordType());
    }

    @Test
    void exampleTest3() {
        var typeMap = modelMapperBasic.createTypeMap(Account.class, AccountDtoWithoutBuilder.class);

        typeMap.addMapping(src -> src.getAddress().getCountry(), (dest, v) -> dest.getContactDetailsDto().setCountry((String) v));

        AccountDtoWithoutBuilder resultAccountDto = modelMapperBasic.map(initAccount, AccountDtoWithoutBuilder.class);

        assertNotNull(resultAccountDto.getContactDetailsDto());
        assertEquals(COUNTRY, resultAccountDto.getContactDetailsDto().getCountry());
    }

    @Test
    void exampleTest4() {
        var typeMap = modelMapperBasic.createTypeMap(Account.class, AccountDtoWithoutBuilder.class);

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

        AccountDtoWithoutBuilder resultAccountDto = modelMapperBasic.map(initAccount, AccountDtoWithoutBuilder.class);

        assertEquals(ID.toString(), resultAccountDto.getMail());
        assertEquals(PasswordType.TO_CENTRALSPACE.toString().toLowerCase() + "_converter", resultAccountDto.getPasswordType());
    }

    @Test
    void exampleTest5() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            ContactDetailsDto resultContactDetailsDto = modelMapperBasic.map(initAccount.getAddress(), ContactDetailsDto.class);
            modelMapperBasic.validate();
        });
        ValidationException validationException = (ValidationException) exception;
    }


    @Test
    void exampleTest1Builder() {
        AccountDtoBuilder accountDtoBuilder = modelMapperForBuilder.map(initAccount, AccountDtoBuilder.class);
        accountDtoBuilder.contactDetailsDto(modelMapperBasic.map(initAccount.getAddress(), ContactDetailsDto.class));
        AccountDto accountDto = accountDtoBuilder.build();
        assertEquals(ID, accountDto.getId());
        assertEquals(LOGIN, accountDto.getLogin());
        assertEquals(MAIL, accountDto.getMail());
        assertEquals(COUNTRY, accountDto.getContactDetailsDto().getCountry());
    }

    @Test
    void exampleTest2Builder() {
        var typeMap = modelMapperForBuilder.createTypeMap(Account.class, AccountDtoBuilder.class);

        Converter<List<Password>, PasswordType> firstPasswordConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().get(0)
                .getPasswordType();

        typeMap.addMappings(mapper -> {
            mapper.using(firstPasswordConverter)
                    .map(Account::getPasswords, AccountDtoBuilder::passwordType);
            mapper.using(ctx -> ctx.getSource() == null ? null : ctx.getSource() + "_converter")
                    .map(Account::getLogin, AccountDtoBuilder::login);
            mapper.skip(AccountDtoBuilder::contactDetailsDto);
            mapper.map(Account::getId, AccountDtoBuilder::idString);
        });

        AccountDto resultAccountDto = modelMapperForBuilder.map(initAccount, AccountDtoBuilder.class).build();

        assertEquals(ID, resultAccountDto.getId());
        assertEquals(ID.toString(), resultAccountDto.getIdString());
        assertEquals(MAIL, resultAccountDto.getMail());
        assertEquals(LOGIN + "_converter", resultAccountDto.getLogin());
        assertEquals(PasswordType.TO_CENTRALSPACE, resultAccountDto.getPasswordType());
    }

    @Test
    void exampleTest3Builder() {
        var typeMap = modelMapperForBuilder.createTypeMap(Account.class, AccountDtoBuilder.class);

        typeMap.addMapping(Account::getAddress, AccountDtoBuilder::contactDetailsDtoFromAddress);

        AccountDto resultAccountDto = modelMapperForBuilder.map(initAccount, AccountDtoBuilder.class).build();

        assertNotNull(resultAccountDto.getContactDetailsDto());
        assertEquals(COUNTRY, resultAccountDto.getContactDetailsDto().getCountry());
    }

    @Test
    void exampleTest4Builder() {
        var typeMap = modelMapperForBuilder.createTypeMap(Account.class, AccountDtoBuilder.class);

        Condition notNull = ctx -> ctx.getSource() != null;
        Converter<List<Password>, PasswordType> firstPasswordConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().get(0)
                .getPasswordType();

        typeMap.addMappings(mapper -> {
            mapper.when(notNull)
                    .using(firstPasswordConverter)
                    .map(Account::getPasswords, AccountDtoBuilder::passwordType);
            mapper.when(ctx -> {
                Long accountId = (Long) ctx.getSource();
                return accountId == 1L;
            }).map(Account::getId, AccountDtoBuilder::mail);
        });

        AccountDto resultAccountDto = modelMapperForBuilder.map(initAccount, AccountDtoBuilder.class).build();

        assertEquals(ID.toString(), resultAccountDto.getMail());
        assertEquals(PasswordType.TO_CENTRALSPACE, resultAccountDto.getPasswordType());
    }

    @Test
    void mapToDtoTestLevelLowTest() {
        AccountMapper accountMapper = new AccountMapper(OperationLevel.LOW);
        AccountDto resultDto = accountMapper.mapToDto(initAccount);

        assertNull(resultDto.getId());
        assertEquals(MAIL, resultDto.getMail());
        assertEquals(LOGIN, resultDto.getLogin());
    }

    @Test
    void mapToDtoTestLevelHighTest() {
        AccountMapper accountMapper = new AccountMapper(OperationLevel.HIGH);
        AccountDto resultDto = accountMapper.mapToDto(initAccount);

        assertEquals(ID, resultDto.getId());
        assertEquals(MAIL, resultDto.getMail());
        assertEquals(LOGIN, resultDto.getLogin());
    }
}
