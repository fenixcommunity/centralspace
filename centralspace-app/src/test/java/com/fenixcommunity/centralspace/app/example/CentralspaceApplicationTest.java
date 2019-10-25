package com.fenixcommunity.centralspace.app.example;

import com.fenixcommunity.centralspace.app.config.DBConfig;
import com.fenixcommunity.centralspace.domain.repository.PasswordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith( SpringRunner.class )
@DataJpaTest
//@ContextConfiguration(classes = {DBConfig.class})
//@AutoConfigurationPackage
@Import(DBConfig.class)
//@ComponentScan(basePackages = "com.fenixcommunity.centralspace")
@EnableJpaRepositories
public class CentralspaceApplicationTest {

    //    @Autowired
//    private PasswordRepository repository;
    @Autowired
    private PasswordRepository repository;


    @Test
    public void test1() {
        Assert.notNull(repository);
    }

    @Test
    public void test2() {
//        Account account = Account.builder()
//                .login(NAME)
//                .email(EMAIL).build();
//        em.persist(account);
//        em.flush();
//
//        assertThat(em).isNotNull();
//        assertThat(repository).isNotNull();
    }

//    @RunWith(SpringJUnit4ClassRunner.class)
//    @ActiveProfiles({NonProductionProfiles.TEST, NonProductionProfiles.MOCKED_INFRASTRUCTURE})
//    @TestPropertySource("classpath:application-test.properties")
//    @ContextConfiguration(classes = {DomainConfig.class, MongoTypeMappingTestConfig.class, ApplyCoreDomainConfig.class, ApplyEnvironmentConfig.class, MockedServicesConfiguration.class})
//    @TestExecutionListeners({
//            ClearInMemoryDatabaseBeforeTestExecutionListener.class,
//            InjectDependenciesOnClearContextTestExecutionListener.class
//    })
//    public class MongoPolymorphicTypesMappingIntegrationTest {

}

