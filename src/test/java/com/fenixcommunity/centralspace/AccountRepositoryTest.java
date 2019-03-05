package com.fenixcommunity.centralspace;

import com.fenixcommunity.centralspace.Account.Account;
import com.fenixcommunity.centralspace.Account.AccountRepository;
import com.fenixcommunity.centralspace.Account.AccountService;
import com.fenixcommunity.centralspace.Password.Password;
import com.fenixcommunity.centralspace.Password.PasswordRepository;
import com.fenixcommunity.centralspace.Password.PasswordType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.mock;

//@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryTest {

    //TODO nie powinno bazowac na prawdziwych danych
//    @InjectMocks
    private AccountService accountService;

//    @Mock
    private AccountRepository accountRepository;

//    @Mock
//    private PasswordRepository passwordRepository;

    private Account account;
    private Password password;
    private Long id;
    private String login;
    private String email;

    @Before
    public void setTest(){
        id = 9999L;
        login = "testLogin";
        email = "test@o2.pl";
//        accountRepository.deleteById(id);
//        passwordRepository.deleteById(id);
        account = Account.builder()
                .id(id)
                .login(login)
                .email(email).build();
        password = Password.builder()
                .id(id)
                .password("testPassword")
                .passwordType(PasswordType.TO_CENTRALSPACE)
                .account(account).build();
        account.setPasswords(Collections.singletonList(password));
        AccountRepository accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void shouldSaveAccountWithUppercaseLogin() {
        Account savedAccount = accountService.createAccount(account);
        System.out.println(savedAccount.toString());
    }
//TODO security wywal z git
}
