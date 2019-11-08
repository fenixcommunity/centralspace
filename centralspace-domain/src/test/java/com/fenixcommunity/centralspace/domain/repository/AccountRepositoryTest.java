package com.fenixcommunity.centralspace.domain.repository;


import com.fenixcommunity.centralspace.domain.config.DomainConfig;
import com.fenixcommunity.centralspace.domain.model.account.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.fenixcommunity.centralspace.utills.common.test.Var.EMAIL;
import static com.fenixcommunity.centralspace.utills.common.test.Var.LOGIN;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = {"classpath:application.properties"})
@ContextConfiguration(classes = DomainConfig.class)
//@AutoConfigureTestDatabase(replace = NONE)
public class AccountRepositoryTest {
    //todo SpringExtension  + https://github.com/antkorwin/junit5-integration-test-utils#postgresql
// todo http://zetcode.com/springboot/datajpatest/
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AccountRepository repository;

    @Test
    public void test4() {
        Account account = Account.builder()
                .login(LOGIN)
                .email(EMAIL).build();
        testEntityManager.persist(account);
        testEntityManager.flush();

        Assert.assertNotNull(repository.findAll());
    }

    @Test
    public void test5() {
        Account account = Account.builder()
                .login(LOGIN)
                .email(EMAIL).build();
        testEntityManager.persist(account);
        testEntityManager.flush();

        Assert.assertNotNull(repository.findByLogin(LOGIN));
    }

    @Test
    public void test3() {
        Assert.assertNotNull(testEntityManager);
    }

}