package com.fenixcommunity.centralspace.app.rest.mapper.modelmapper;

import static com.fenixcommunity.centralspace.utilities.common.Var.CITY;
import static com.fenixcommunity.centralspace.utilities.common.Var.COUNTRY;
import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD_CHAR_ARRAY;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fenixcommunity.centralspace.app.rest.mapper.modelmapper.AccountDtoToTest.AccountDtoToTestBuilder;
import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.account.ContactDetailsDto;
import com.fenixcommunity.centralspace.app.rest.mapper.account.modelmapper.AccountModelMapper;
import com.fenixcommunity.centralspace.utilities.mapper.ModelMapperBuilder;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import com.fenixcommunity.centralspace.domain.model.permanent.password.PasswordType;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime dateTime;

    private ModelMapper modelMapperBasic;
    private ModelMapper modelMapperFromBuilder;
    private Account initAccount;

    @BeforeEach
    public void before() {
        modelMapperBasic = new ModelMapperBuilder()
                .withMatchingStrategy(MatchingStrategies.STANDARD)
                .build();
        modelMapperFromBuilder = new ModelMapperBuilder()
                .withUsingLombokBuilderForDestination()
                .withMethodAccessLevelToMapping(AccessLevel.PUBLIC)
                .withNameConvention(NameTokenizers.CAMEL_CASE, NameTokenizers.UNDERSCORE)
                .build();

        initAccount = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL)
                .build();
        List<Password> passwords = singletonList(new Password(ID, initAccount, PASSWORD_CHAR_ARRAY, PasswordType.TO_CENTRALSPACE));
        initAccount.setPasswords(passwords);
        Address address = new Address(ID, COUNTRY, CITY, singletonList(initAccount));
        initAccount.setAddress(address);
    }

    @Test
    void exampleTest1() {
        AccountDtoWithoutBuilderToTest resultAccountDto = modelMapperBasic.map(initAccount, AccountDtoWithoutBuilderToTest.class);
        ContactDetailsDto resultContactDetailsDto = modelMapperBasic.map(initAccount.getAddress(), ContactDetailsDto.class);
        assertEquals(ID, resultAccountDto.getId());
        assertEquals(LOGIN, resultAccountDto.getLogin());
        assertEquals(MAIL, resultAccountDto.getMail());
        assertEquals(COUNTRY, resultContactDetailsDto.getCountry());
    }

    @Test
    void exampleTest2() {
        var typeMap = modelMapperBasic.createTypeMap(Account.class, AccountDtoWithoutBuilderToTest.class);

        Converter<List<Password>, String> firstPasswordConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().get(0)
                .getPasswordType().toString().toLowerCase() + "_converter";

        typeMap.addMappings(mapper -> {
            mapper.using(firstPasswordConverter)
                    .map(Account::getPasswords, AccountDtoWithoutBuilderToTest::setPasswordType);
            mapper.using(ctx -> ctx.getSource() == null ? null : ctx.getSource() + "_converter")
                    .map(Account::getLogin, AccountDtoWithoutBuilderToTest::setLogin);
            mapper.skip(AccountDtoWithoutBuilderToTest::setContactDetailsDto);
            mapper.map(Account::getId, AccountDtoWithoutBuilderToTest::setIdString);
        });

        AccountDtoWithoutBuilderToTest resultAccountDto = modelMapperBasic.map(initAccount, AccountDtoWithoutBuilderToTest.class);

        assertEquals(ID, resultAccountDto.getId());
        assertEquals(ID.toString(), resultAccountDto.getIdString());
        assertEquals(MAIL, resultAccountDto.getMail());
        assertEquals(LOGIN + "_converter", resultAccountDto.getLogin());
        assertEquals(PasswordType.TO_CENTRALSPACE.toString().toLowerCase() + "_converter", resultAccountDto.getPasswordType());
    }

    @Test
    void exampleTest3() {
        var typeMap = modelMapperBasic.createTypeMap(Account.class, AccountDtoWithoutBuilderToTest.class);

        typeMap.addMapping(src -> src.getAddress().getCountry(), (dest, v) -> dest.getContactDetailsDto().setCountry((String) v));

        AccountDtoWithoutBuilderToTest resultAccountDto = modelMapperBasic.map(initAccount, AccountDtoWithoutBuilderToTest.class);

        assertNotNull(resultAccountDto.getContactDetailsDto());
        assertEquals(COUNTRY, resultAccountDto.getContactDetailsDto().getCountry());
    }

    @Test
    void exampleTest4() {
        var typeMap = modelMapperBasic.createTypeMap(Account.class, AccountDtoWithoutBuilderToTest.class);

        Condition notNull = ctx -> ctx.getSource() != null;
        Converter<List<Password>, String> firstPasswordConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().get(0)
                .getPasswordType().toString().toLowerCase() + "_converter";

        typeMap.addMappings(mapper -> {
            mapper.when(notNull)
                    .using(firstPasswordConverter)
                    .map(Account::getPasswords, AccountDtoWithoutBuilderToTest::setPasswordType);
            mapper.when(ctx -> {
                Long accountId = (Long) ctx.getSource();
                return accountId == 1L;
            }).map(Account::getId, AccountDtoWithoutBuilderToTest::setMail);
        });

        AccountDtoWithoutBuilderToTest resultAccountDto = modelMapperBasic.map(initAccount, AccountDtoWithoutBuilderToTest.class);

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
        AccountDtoToTestBuilder accountDtoBuilder = modelMapperFromBuilder.map(initAccount, AccountDtoToTestBuilder.class);
        accountDtoBuilder.contactDetailsDto(modelMapperBasic.map(initAccount.getAddress(), ContactDetailsDto.class));
        AccountDtoToTest accountDto = accountDtoBuilder.build();

        assertEquals(ID, accountDto.getId());
        assertEquals(LOGIN, accountDto.getLogin());
        assertEquals(MAIL, accountDto.getMail());
        assertEquals(COUNTRY, accountDto.getContactDetailsDto().getCountry());
    }

    @Test
    void exampleTest2Builder() {
        var typeMap = modelMapperFromBuilder.createTypeMap(Account.class, AccountDtoToTestBuilder.class);

        Converter<List<Password>, PasswordType> firstPasswordConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().get(0)
                .getPasswordType();

        typeMap.addMappings(mapper -> {
            mapper.using(firstPasswordConverter)
                    .map(Account::getPasswords, AccountDtoToTestBuilder::passwordType);
            mapper.using(ctx -> ctx.getSource() == null ? null : ctx.getSource() + "_converter")
                    .map(Account::getLogin, AccountDtoToTestBuilder::login);
            mapper.skip(AccountDtoToTestBuilder::contactDetailsDto);
            mapper.map(Account::getId, AccountDtoToTestBuilder::idString);
        });

        AccountDtoToTest resultAccountDto = modelMapperFromBuilder.map(initAccount, AccountDtoToTestBuilder.class).build();

        assertEquals(ID, resultAccountDto.getId());
        assertEquals(ID.toString(), resultAccountDto.getIdString());
        assertEquals(MAIL, resultAccountDto.getMail());
        assertEquals(LOGIN + "_converter", resultAccountDto.getLogin());
        assertEquals(PasswordType.TO_CENTRALSPACE, resultAccountDto.getPasswordType());
    }

    @Test
    void exampleTest3Builder() {
        var typeMap = modelMapperFromBuilder.createTypeMap(Account.class, AccountDtoToTestBuilder.class);

        typeMap.addMapping(Account::getAddress, AccountDtoToTestBuilder::contactDetailsDtoFromAddress);

        AccountDtoToTest resultAccountDto = modelMapperFromBuilder.map(initAccount, AccountDtoToTestBuilder.class).build();

        assertNotNull(resultAccountDto.getContactDetailsDto());
        assertEquals(COUNTRY, resultAccountDto.getContactDetailsDto().getCountry());
    }

    @Test
    void exampleTest4Builder() {
        var typeMap = modelMapperFromBuilder.createTypeMap(Account.class, AccountDtoToTestBuilder.class);

        Condition notNull = ctx -> ctx.getSource() != null;
        Converter<List<Password>, PasswordType> firstPasswordConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().get(0)
                .getPasswordType();

        typeMap.addMappings(mapper -> {
            mapper.when(notNull)
                    .using(firstPasswordConverter)
                    .map(Account::getPasswords, AccountDtoToTestBuilder::passwordType);
            mapper.when(ctx -> {
                Long accountId = (Long) ctx.getSource();
                return accountId == 1L;
            }).map(Account::getId, AccountDtoToTestBuilder::mail);
        });

        AccountDtoToTest resultAccountDto = modelMapperFromBuilder.map(initAccount, AccountDtoToTestBuilder.class).build();

        assertEquals(ID.toString(), resultAccountDto.getMail());
        assertEquals(PasswordType.TO_CENTRALSPACE, resultAccountDto.getPasswordType());
    }

    @Test
    void mapToDtoTestLevelLowTest() {
        AccountMapperToTest accountMapper = new AccountMapperToTest(OperationLevel.LOW);
        AccountDtoToTest resultDto = accountMapper.mapToDto(initAccount);

        assertNull(resultDto.getId());
        assertEquals(MAIL, resultDto.getMail());
        assertEquals(LOGIN, resultDto.getLogin());
    }

    @Test
    void mapToDtoTestLevelHighTest() {
        AccountMapperToTest accountMapper = new AccountMapperToTest(OperationLevel.HIGH);
        AccountDtoToTest resultDto = accountMapper.mapToDto(initAccount);

        assertEquals(ID, resultDto.getId());
        assertEquals(MAIL, resultDto.getMail());
        assertEquals(LOGIN, resultDto.getLogin());
    }

    @Test
    void mapFromDtoAndToDtoTest() {
        AccountDtoToTest accountDtoToTest = AccountDtoToTest.builder()
                .id(ID)
                .login(LOGIN)
                .mail("max@o2.pl")
                .build();

        AccountMapperToTest accountMapper = new AccountMapperToTest(OperationLevel.LOW);

        Account resultAccount = accountMapper.mapFromDto(accountDtoToTest);
        AccountDtoToTest resultDto = accountMapper.mapToDto(initAccount);

        assertNotNull(resultAccount);
        assertEquals(LOGIN, resultAccount.getLogin());
        assertEquals("max@o2.pl", resultAccount.getMail());

        assertNotNull(resultDto);
        assertEquals(initAccount.getLogin(), resultDto.getLogin());
        assertEquals(initAccount.getMail(), resultDto.getMail());
    }

    @Test
    void mapFromDtoAndToDtoWithContactDetailsTest() {
        AccountDto accountDto = AccountDto.builder()
                .id(ID)
                .mail(MAIL)
                .contactDetailsDto(new ContactDetailsDto("England", "+555444666"))
                .build();
        AccountModelMapper accountModelMapper = new AccountModelMapper(OperationLevel.HIGH);
        Account resultAccount = accountModelMapper.mapFromDto(accountDto);
        AccountDto resultDto = accountModelMapper.mapToDto(initAccount);

        assertNotNull(resultAccount);
        assertEquals(ID, resultAccount.getId());
        assertEquals(MAIL, resultAccount.getMail());
        assertNotNull(resultAccount.getAddress());
        assertEquals("England", resultAccount.getAddress().getCountry());

        assertNotNull(resultDto);
        assertEquals(initAccount.getId(), resultDto.getId());
        assertEquals(initAccount.getLogin(), resultDto.getLogin());
        assertEquals(COUNTRY, resultDto.getContactDetailsDto().getCountry());
    }
}
