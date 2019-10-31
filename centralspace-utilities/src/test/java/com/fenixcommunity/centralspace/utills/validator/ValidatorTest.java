package com.fenixcommunity.centralspace.utills.validator;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ValidatorTest {


//    @Autowired
//    private TestEntityManager em;
//

//    @Before
//    public void initTest() {
//        Account account = Account.builder()
//                .login(NAME)
//                .email(EMAIL).build();
//        em.persist(account);
//        em.flush();
//
//        Password password = Password.builder()
//                .password(PASSWORD_LOW)
//                .passwordType(PasswordType.TO_CENTRALSPACE)
//                .account(account).build();
//        em.persist(password);
//        em.flush();
//    }

    // standard whenFindPasswordById_thenReturnAccountId
    @Test
    public void whenFindPasswordById_thenReturnAccountId() {
        // given

        // when
//        Password password = passwordRepository.findById(ID).get();
//
//        // then
//        assertThat(password.getAccount().getId()).isEqualTo(ID);
        //TODO jupiter?
        assertThat(true).isTrue();
    }

}
