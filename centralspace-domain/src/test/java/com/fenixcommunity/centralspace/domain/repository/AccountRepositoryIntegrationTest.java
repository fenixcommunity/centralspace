package com.fenixcommunity.centralspace.domain.repository;

import static com.fenixcommunity.centralspace.utilities.common.Var.ADMIN_USER;
import static com.fenixcommunity.centralspace.utilities.common.Var.CITY;
import static com.fenixcommunity.centralspace.utilities.common.Var.COUNTRY;
import static com.fenixcommunity.centralspace.utilities.common.Var.DB_USER;
import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN_UPPER;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static java.util.Collections.singletonList;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.DEFAULT;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;

import com.fenixcommunity.centralspace.domain.configuration.DomainConfigForTest;
import com.fenixcommunity.centralspace.domain.configuration.db.permanent.PostgresDomainConfig;
import com.fenixcommunity.centralspace.domain.dto.AccountDataDto;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import com.fenixcommunity.centralspace.domain.model.permanent.password.PasswordType;
import com.fenixcommunity.centralspace.domain.repository.permanent.account.AccountRepository;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
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
@ContextConfiguration(classes = PostgresDomainConfig.class) // or custom DomainConfigForTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlGroup({
        @Sql(scripts = {"classpath:/script/schema-test.sql"},
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(encoding = "utf-8", transactionMode = DEFAULT)
        )
})
@FieldDefaults(level = PRIVATE)
@AutoConfigureMockMvc @WithMockUser(username = ADMIN_USER, authorities = {"ROLE_ADMIN"})
public class AccountRepositoryIntegrationTest {
// run always with flag spring.profiles.active=test
    private static final long ACCOUNT_ID_FROM_QUERY = 99L;
    private static final long ACCOUNT_ADMIN_ID_FROM_QUERY = 100L;
    public static final String ACCOUNT_LOGIN_FROM_QUERY = "LOGINQUERY";
    public static final String TEST_MAIL = "text@gmail.com";

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
        createAccount(LOGIN, MAIL, PASSWORD);
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
        assertNotNull(accountRepository.findByLogin(ACCOUNT_LOGIN_FROM_QUERY));
        assertNotNull(foundAccount.orElse(null));
    }

    @Test
    public void findByLoginsOrderedByLoginDescTest() {
        List<Account> foundAccounts = accountRepository.findByLogins(List.of("LOGIN_OTHER_1", "LOGIN_OTHER_2"), Sort.by("login").descending());
        assertNotNull(foundAccounts);
        assertThat(foundAccounts).hasSize(2);
        assertThat(foundAccounts.get(0).getLogin()).isEqualTo("LOGIN_OTHER_2");
    }

    @Test
    public void updateAccountLoginTest() {
        String changed_login = "CHANGED_LOGIN";
        int updatedAccountCount = accountRepository.updateLogin(ACCOUNT_ADMIN_ID_FROM_QUERY, changed_login);
        assertThat(updatedAccountCount).isEqualTo(1);
        Optional<Account> account = accountRepository.findById(ACCOUNT_ADMIN_ID_FROM_QUERY);
        assertNotNull(account.orElse(null));
        assertThat(account.get().getLogin()).isEqualTo(changed_login);
    }

    @Test
    public void findByLoginWithPaginationTest() {
        Pageable secondPageWithTwoElements = PageRequest.of(1, 2, Sort.by("id").descending());
        Page<Account> foundAllAccountsWithPagination = accountRepository.findAllWithPagination(secondPageWithTwoElements);

        assertNotNull(foundAllAccountsWithPagination);
        assertThat(foundAllAccountsWithPagination.getTotalElements()).isEqualTo(18);
        assertThat(foundAllAccountsWithPagination.getTotalPages()).isEqualTo(9);
        assertThat(foundAllAccountsWithPagination.getContent())
                .hasSize(2)
                .extracting("id", "login")
                .containsExactly(
                        tuple(25L, "MAXLOGIN12321"),
                        tuple(37L, "MAX2131LOGIN")
                );
    }

    @Test
    public void findAccountsByEmailsTest() {
        List<Account> foundAccounts = accountRepository.findAccountsByEmails(Set.of("mail_other_1@mail.com", "mail_other_2@mail.com"));
        assertNotNull(foundAccounts);
        assertThat(foundAccounts).hasSize(2);

        Comparator<Account> idComparator = (a1, a2) -> a1.getId().compareTo(a2.getId());
        foundAccounts.sort(idComparator.thenComparing(Account::getMail).reversed());

        assertThat(foundAccounts.get(0).getLogin()).isEqualTo("LOGIN_OTHER_2");
    }

    @Test
    public void findByLoginWithNullArgReturnsNullTest() {
        // then where login is null
        assertDoesNotThrow(() -> accountRepository.findByLogin(null));
        assertDoesNotThrow(() -> accountRepository.findAccountsByEmails(null));
        assertDoesNotThrow(() -> {
            accountRepository.findAllWithPagination(null);
            accountRepository.findByLogins(null, Sort.by("login").descending());
        });
    }

    @Test
    public void findByLoginDynamicProjectionsTest() {
            Account account = accountRepository.findByLogin(LOGIN_UPPER);
            AccountDataDto accountDataDto = accountRepository.findByMailOrderByMailAsc(AccountDataDto.class, TEST_MAIL);
            assertThat(account).isNotNull();
            assertThat(accountDataDto).isNotNull();
            assertThat(account.getLogin()).isEqualTo(LOGIN_UPPER);
            assertThat(accountDataDto.getMail()).isEqualTo(TEST_MAIL);
    }

    @Test
    public void findFirstAndTopTest() {
        Optional<Account> accountFirst = accountRepository.findFirstByOrderByLoginDesc();
        Optional<Account> accountTop = accountRepository.findTopByOrderByLogin();
        assertThat(accountFirst.isPresent()).isTrue();
        assertThat(accountTop.isPresent()).isTrue();
        assertThat(accountFirst.get().getLogin()).isNotEqualTo(accountTop.get().getLogin());
    }

    @Test
    public void findFirst10ByLoginId() {
        List<Account> accounts = accountRepository.findFirst10ByOrderById();
        assertThat(accounts).isNotEmpty();
        assertThat(accounts.size()).isGreaterThan(1);

    }

    private void createAccount(String login, String mail, String providedPassword) {
        Address address = Address.builder()
                .country(COUNTRY)
                .city(CITY)
                .build();
        Long addressId = (Long) testEntityManager.persistAndGetId(address);
        address.setId(addressId);

        Account account = Account.builder()
                .login(login)
                .mail(mail)
                .address(address)
                .build();
        Long accountId = (Long) testEntityManager.persistAndGetId(account);
        account.setId(accountId);

        Password password = Password.builder()
                .password(providedPassword)
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
}