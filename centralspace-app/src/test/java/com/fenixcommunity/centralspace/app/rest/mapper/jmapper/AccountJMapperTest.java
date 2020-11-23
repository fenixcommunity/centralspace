package com.fenixcommunity.centralspace.app.rest.mapper.jmapper;

import static com.fenixcommunity.centralspace.utilities.common.Var.CITY;
import static com.fenixcommunity.centralspace.utilities.common.Var.COUNTRY;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.fenixcommunity.centralspace.app.rest.dto.account.AccountJMapperDto;
import com.fenixcommunity.centralspace.app.rest.dto.account.ContactDetailsDto;
import com.fenixcommunity.centralspace.app.rest.mapper.account.jmapper.AccountJMapper;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import com.fenixcommunity.centralspace.domain.model.permanent.password.PasswordType;
import org.junit.jupiter.api.Test;

class AccountJMapperTest {

    @Test
    void mapToDtoTest() {
        Account account = initAccount();
        List<AccountJMapperDto> accountDtos = new AccountJMapper().mapToDtoList(List.of(account, account));
        accountDtos.forEach(accountDto -> {
            assertThat(accountDto.getId()).isNull();
            assertThat(accountDto.getMail()).isEqualTo(account.getMail());
            assertThat(accountDto.getUniqueLogin()).isEqualTo(account.getLogin());
            assertThat(accountDto.getIdentifier()).isEqualTo(account.getNip());
            ContactDetailsDto contactDetails = accountDto.getContactDetails();
            assertThat(contactDetails).isNotNull();
            assertThat(contactDetails.getCountry()).isEqualTo(COUNTRY);
        });
    }

    @Test
    void mapFromDtoTest() {
        AccountJMapperDto accountDto = new AccountJMapperDto();
        accountDto.setUniqueLogin(LOGIN);
        accountDto.setMail(MAIL);

        List<Account> accounts = new AccountJMapper().mapFromDtoList(List.of(accountDto, accountDto));
        accounts.forEach(account -> {
            assertThat(account.getId()).isNull();
            assertThat(account.getMail()).isEqualTo(accountDto.getMail());
            assertThat(account.getLogin()).isEqualTo(accountDto.getUniqueLogin());
        });
    }

    private Account initAccount() {
        Address address = Address.builder()
                .country(COUNTRY)
                .city(CITY)
                .build();
        address.setId(2L);

        Account account = Account.builder()
                .login(LOGIN)
                .mail(MAIL)
                .address(address)
                .build();
        account.setId(1L);

        Password password = Password.builder()
                .password("dfdf")
                .account(account)
                .passwordType(PasswordType.TO_CENTRALSPACE)
                .build();

        account.setPasswords(List.of(password));

        return account;
    }
}