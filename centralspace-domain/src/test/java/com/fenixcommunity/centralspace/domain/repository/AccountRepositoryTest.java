package com.fenixcommunity.centralspace.domain.repository;

import com.fenixcommunity.centralspace.domain.configuration.DomainConfigForTest;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.domain.repository.mounted.AccountRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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

import javax.sql.DataSource;
import java.util.List;

import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = {"classpath:domain-test.properties"})
@ContextConfiguration(classes = DomainConfigForTest.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlGroup({
        @Sql(scripts = {"classpath:/script/schema-test.sql"},
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.DEFAULT)
        )
})
// zastap data.sql wtedy ttlko raz
public class AccountRepositoryTest {
    //todo SpringExtension  + https://github.com/antkorwin/junit5-integration-test-utils#postgresql
// todo http://zetcode.com/springboot/datajpatest/
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeAll
    void init() {
        account = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL).build();
        testEntityManager.persist(account);
        testEntityManager.flush();
    }

    @Test
    public void repoInitTest() {
        assertNotNull(testEntityManager);
        assertNotNull(dataSource);
    }

    @Test
    public void repoTest1() {
//        Account account = Account.builder()
//                .login(LOGIN)
//                .mail(MAIL).build();
//        testEntityManager.persist(account);
//        testEntityManager.flush();

        assertNotNull(accountRepository.findAll());
    }

    @Test
    public void repoTest2() {
//        Account account = Account.builder()
//                .login(LOGIN)
//                .mail(MAIL).build();
//        testEntityManager.persist(account);
//        testEntityManager.flush();

        assertNotNull(accountRepository.findById(ID));
    }

    @Test
    public void repoTest3() {
//        Account account = Account.builder()
//                .login(LOGIN)
//                .mail(MAIL).build();
//        testEntityManager.persist(account);
//        testEntityManager.flush();
        assertNotNull(accountRepository.findByLogin(LOGIN));
    }

    @Test
    public void repoTest4() {
        List<Account> accounts = (List<Account>) accountRepository.findAll();
        Account foundAccount = accountRepository.findById(99L).get();
        Account foundAccount2 = accountRepository.findByLogin("test");
        assertThat(accounts).extracting(Account::getLogin).containsOnly("test");
        assertNotNull(foundAccount);
        assertNotNull(foundAccount2);
    }

    todo


}