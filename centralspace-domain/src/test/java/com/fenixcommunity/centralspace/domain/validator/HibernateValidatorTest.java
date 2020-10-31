package com.fenixcommunity.centralspace.domain.validator;

import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.NIP;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;
import javax.money.Monetary;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.AccountBalance;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HibernateValidatorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateCorrectAccountBalanceTest() {
        AccountBalance accountBalance = AccountBalance.builder()
                .creditCardNumber("7992-7398-713")
                .moneyAmount(Money.of(new BigDecimal("100.0"), Monetary.getCurrency("EUR")))
                .interestRate(new BigDecimal("99.3"))
                .balanceDuration(Duration.ofDays(245))
                .balanceInternalUrl("https://www.balance.com/account/2")
                .build();

        Set<ConstraintViolation<AccountBalance>> violations = validator.validate(accountBalance);
        assertThat(violations).isEmpty();
    }

    @Test
    void validateNotCorrectAccountBalanceTest() {
        AccountBalance accountBalance = AccountBalance.builder()
                .creditCardNumber("7992-7398-713-2321321")
                .moneyAmount(Money.of(new BigDecimal("100.0"), Monetary.getCurrency("USD")))
                .interestRate(new BigDecimal("199.3"))
                .balanceDuration(Duration.ofDays(366))
                .balanceInternalUrl("http://www.balance.com/account/2")
                .build();

        Set<ConstraintViolation<AccountBalance>> violations = validator.validate(accountBalance);
        assertThat(violations).hasSize(5);
    }

    @Test
    void validateCorrectAccountTest() {
        Account account = Account.builder()
                .id(ID)
                .login(LOGIN)
                .nip(NIP)
                .build();

        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertThat(violations).isEmpty();
    }

    @Test
    void validateNotCorrectAccountTest() {
        Account account = Account.builder()
                .id(ID)
                .login("very_long_login_o_my_god_212122121")
                .pesel("945353")
                .nip("682-114-3")
                .build();

        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        assertThat(violations).hasSize(3);
    }
}
