package com.fenixcommunity.centralspace.domain.repository;

import static com.fenixcommunity.centralspace.utilities.common.Var.CITY;
import static com.fenixcommunity.centralspace.utilities.common.Var.COUNTRY;
import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN_UPPER;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static java.util.Collections.singletonList;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.DEFAULT;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import com.fenixcommunity.centralspace.domain.configuration.DomainConfigForTest;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import com.fenixcommunity.centralspace.domain.model.permanent.password.PasswordType;
import com.fenixcommunity.centralspace.domain.repository.permanent.AccountRepository;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) @DataJpaTest
/*If you want to use Spring Custom Method ..findByLogin please extend to:
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)*/
@TestPropertySource(locations = {"classpath:domain-test.yml"})
@ContextConfiguration(classes = DomainConfigForTest.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlGroup({
        @Sql(scripts = {"classpath:/script/schema-test.sql"},
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(encoding = "utf-8", transactionMode = DEFAULT)
        )
})
@FieldDefaults(level = PRIVATE)
public class AccountRepositoryTest {

    private static final long ACCOUNT_ID_FROM_QUERY = 99L;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountRepository accountRepository;

    // used junit4, no jupiter
    @Before
    public void init() {
        // magic, we don't need class var in Before method
        Address address = Address.builder()
        .country(COUNTRY)
        .city(CITY)
        .build();
        Long addressId = (Long) testEntityManager.persistAndGetId(address);
        address.setId(addressId);

        Account account = Account.builder()
                .login(LOGIN)
                .mail(MAIL)
                .address(address)
                .build();
        Long accountId = (Long) testEntityManager.persistAndGetId(account);
        account.setId(accountId);

        Password password = Password.builder()
                .password(PASSWORD)
                .account(account)
                .passwordType(PasswordType.TO_CENTRALSPACE)
                .build();
        testEntityManager.persistAndGetId(password);
        Long passwordId = (Long) testEntityManager.persistAndGetId(password);
        password.setId(passwordId);

        testEntityManager.flush();

        account.setPasswords(singletonList(password));
        testEntityManager.refresh(account);

        testEntityManager.flush();
    }

    @Test
    public void repoInitTest() {
        assertNotNull(testEntityManager);
        assertNotNull(accountRepository);
        assertNotNull(dataSource);
    }

    @Test
    public void repoTest() {
        assertNotNull(accountRepository.findById(ID));
        assertNotNull(accountRepository.findByLogin(LOGIN_UPPER));
    }

    @Test
    public void repoExtractingTest() {
        List<Account> accounts = accountRepository.findAll();
        assertNotNull(accounts);
        assertThat(accounts).extracting(Account::getLogin).containsAnyOf(LOGIN_UPPER);
    }

    @Test
    public void repoFromExecutedQueryTest() {
        Optional<Account> foundAccount = accountRepository.findById(ACCOUNT_ID_FROM_QUERY);
        assertNotNull(accountRepository.findByLogin("LOGINQUERY"));
        assertNotNull(foundAccount.orElse(null));
    }
}