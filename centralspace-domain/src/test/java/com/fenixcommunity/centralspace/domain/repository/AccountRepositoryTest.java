package com.fenixcommunity.centralspace.domain.repository;

import com.fenixcommunity.centralspace.domain.configuration.DomainConfigForTest;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.domain.repository.mounted.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = {"classpath:domain-test.properties"})
@ContextConfiguration(classes = DomainConfigForTest.class)
//@AutoConfigureTestDatabase(replace = NONE)
@SqlGroup({
        @Sql(scripts = {"classpath:/script/test_script.sql"},
                config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.DEFAULT))
})
public class AccountRepositoryTest {
    //todo SpringExtension  + https://github.com/antkorwin/junit5-integration-test-utils#postgresql
// todo http://zetcode.com/springboot/datajpatest/
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountRepository repository;
//    check

    @Test
    public void repoInitTest() {
        assertNotNull(testEntityManager);
        assertNotNull(dataSource);
    }

    @Test
    public void repoTest1() {
        Account account = Account.builder()
                .login(LOGIN)
                .mail(MAIL).build();
        testEntityManager.persist(account);
        testEntityManager.flush();

        assertNotNull(repository.findAll());
    }

    @Test
    public void repoTest2() {
        Account account = Account.builder()
                .login(LOGIN)
                .mail(MAIL).build();
        testEntityManager.persist(account);
        testEntityManager.flush();

        assertNotNull(repository.findByLogin(LOGIN));
    }

    @Test
    public void repoTest3() {
        assertNotNull(repository.findByLogin("test"));
    }

}