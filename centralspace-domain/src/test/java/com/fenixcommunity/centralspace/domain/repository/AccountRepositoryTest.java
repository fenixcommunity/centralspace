package com.fenixcommunity.centralspace.domain.repository;


import com.fenixcommunity.centralspace.domain.config.DomainConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith( SpringRunner.class )
@DataJpaTest
@ContextConfiguration(classes=DomainConfig.class)
@AutoConfigureTestDatabase(replace = NONE)
//@EntityScan({"com.fenixcommunity.centralspace.domain"})
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private sss as;

    @Autowired
    AccountRepository repository;


    @Test
    public void test() {
        Assert.assertTrue(true);
    }

    @Test
    public void test2() {
        Assert.assertNotNull(as);
    }

    @Test
    public void test4() {
        Assert.assertNotNull(repository);
    }

    @Test
    public void test3() {
        Assert.assertNotNull(testEntityManager);
    }

}
