package com.fenixcommunity.centralspace.domain.model.permanent.account;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.time.Duration;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fenixcommunity.centralspace.domain.converter.MonetaryAmountConverter;
import com.fenixcommunity.centralspace.domain.core.listener.AccountEntityListener;
import com.fenixcommunity.centralspace.domain.model.permanent.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Currency;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.time.DurationMax;
import org.javamoney.moneta.Money;

@Entity @Table(name = "account_balance") @EntityListeners(AccountEntityListener.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true) @ToString() @FieldDefaults(level = PRIVATE)
public class AccountBalance extends AbstractBaseEntity {
    private static final long serialVersionUID = 4226113478160491363L;

    //todo add to account

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Convert(converter = MonetaryAmountConverter.class)
    @Currency("EUR")
    @Column(name = "money_amount")
    private Money moneyAmount;

    @CreditCardNumber(ignoreNonDigitCharacters = true)
    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @Range(min = 0, max = 100)
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @DurationMax(days = 365, hours = 1)
    @Column(name = "balance_duration")
    private Duration balanceDuration;

    @URL(protocol = "https") // @SafeHtml
    @Column(name = "balance_internal_url")
    private String balanceInternalUrl;
}