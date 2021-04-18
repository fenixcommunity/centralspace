package com.fenixcommunity.centralspace.app.service;

import static com.fenixcommunity.centralspace.utilities.common.Var.anyLong;
import static com.fenixcommunity.centralspace.utilities.common.Var.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fenixcommunity.centralspace.app.service.account.AccountService;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import com.fenixcommunity.centralspace.domain.model.permanent.password.PasswordType;
import com.fenixcommunity.centralspace.domain.repository.permanent.account.AccountRepository;
import com.fenixcommunity.centralspace.utilities.async.AsyncFutureHelper;
import com.fenixcommunity.centralspace.utilities.resourcehelper.AppFileUtils;
import com.fenixcommunity.centralspace.utilities.test.PrepareTestExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @RegisterExtension
    @SuppressWarnings("unused")
    static PrepareTestExtension prepareTestExtension = new PrepareTestExtension("some info");

    private static final String MAPPINGS_PATH = "json/accountMappings.json";
    private static String loadedAccountMappings;
    private List<Account> accounts;

    @BeforeAll
    void loadFiles() {
        loadedAccountMappings = AppFileUtils.loadFileAsString(MAPPINGS_PATH);
    }

    @BeforeEach
    public void init() {
        Address address = getAddress();

        Account account1 = getAccount(1L, address);
        account1.setPasswords(List.of(getPassword(account1)));

        Account account2 = getAccount(2L, address);
        account2.setPasswords(List.of(getPassword(account2)));

        accounts = List.of(account1, account2);
        when(accountRepository.findAll(any(Sort.class))).thenReturn(accounts);
    }

    @Test
    void loadedJsonTest() {
        assertThat(loadedAccountMappings).isNotBlank();
        assertThat(loadedAccountMappings).contains("account2");
    }

    @Test
    void futureFindAllTest() {
        //GIVEN

        //WHEN
        CompletableFuture<List<Account>> future = accountService.findAll();
        List<Account> accounts = AsyncFutureHelper.get(future);

        //THEN
        assertThat(accounts.size()).isEqualTo(2);
    }


    private Account getAccount(Long id, Address address) {
        return Account.builder()
                .id(1L)
                .login(anyString())
                .mail(anyString())
                .address(address)
                .build();
    }

    private Address getAddress() {
        return Address.builder()
                .id(anyLong())
                .country(anyString())
                .city(anyString())
                .build();
    }

    private Password getPassword(Account account) {
        return Password.builder()
                .id(anyLong())
                .password(any())
                .account(account)
                .passwordType(PasswordType.TO_CENTRALSPACE)
                .build();
    }

}