package com.fenixcommunity.centralspace.domain.converter;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContextBuilder;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fenixcommunity.centralspace.domain.exception.converter.CryptoJpaConverterException;
import org.javamoney.moneta.Money;
import org.yaml.snakeyaml.Yaml;

@Converter
public class MonetaryAmountConverter implements AttributeConverter<Money, BigDecimal> {
    private static final String GLOBAL_CUSTOMIZATION_FILE = "globalcustomization.yml";
    private static final String GLOBAL_CURRENCY_PROPERTY_KEY = "globalCurrency";
    private static final String defaultCurrency;

    static {
        final Yaml yaml = new Yaml();
        final InputStream inputStream = CryptoJpaConverter.class.getClassLoader().getResourceAsStream(GLOBAL_CUSTOMIZATION_FILE);
        final Map<String, Object> properties = yaml.load(inputStream);
        if (properties.isEmpty()) {
            throw new CryptoJpaConverterException("Invalid properties");
        }
        final String globalCurrencyProperty = (String) properties.get(GLOBAL_CURRENCY_PROPERTY_KEY);

        if (isBlank(globalCurrencyProperty)) {
            throw new CryptoJpaConverterException("Invalid parameters of properties");
        }
        defaultCurrency = globalCurrencyProperty;
    }

    @Override
    public BigDecimal convertToDatabaseColumn(final Money toDbData) {
        return toDbData != null ? toDbData.getNumberStripped() : null;
    }

    @Override
    public Money convertToEntityAttribute(final BigDecimal fromDbData) {
        if (fromDbData == null) {
            return null;
        }
//      or NumberFormat.getCurrencyInstance(new Locale("en", "IE")).getCurrency();
//      fromDbData as String "USD 1"-> MonetaryAmountFormat formatUSD = MonetaryFormats.getAmountFormat(Locale.US);
        final CurrencyUnit currencyUnit = Monetary.getCurrency(defaultCurrency);
        final Money money = Money.of(fromDbData, currencyUnit, MonetaryContextBuilder.of().build());

        final MonetaryAmount moneyBonusxD = Monetary.getDefaultAmountFactory()
                .setCurrency(currencyUnit).setNumber(777).create();
        money.add(moneyBonusxD);

        return money;
    }
}