package com.fenixcommunity.centralspace.domain.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = {"classpath:globalcustomization.yml"})
class MonetaryAmountConverterTest {

    @Test
    void convertCorrectTest() {
        MonetaryAmountConverter monetaryAmountConverter = new MonetaryAmountConverter();

        BigDecimal inputMoneyAmount = new BigDecimal("12.2");
        Money money = Money.of(inputMoneyAmount, "EUR");

        BigDecimal convertToDatabaseResult = monetaryAmountConverter.convertToDatabaseColumn(money);
        assertThat(convertToDatabaseResult).isEqualTo(inputMoneyAmount);

        Money convertedToEntityAttributeResult = monetaryAmountConverter.convertToEntityAttribute(inputMoneyAmount);
        assertThat(convertedToEntityAttributeResult).isNotNull();
        assertThat(convertedToEntityAttributeResult.getNumberStripped()).isEqualTo(inputMoneyAmount);
        assertThat(convertedToEntityAttributeResult.getCurrency().getCurrencyCode()).isEqualTo("EUR");
    }
}
