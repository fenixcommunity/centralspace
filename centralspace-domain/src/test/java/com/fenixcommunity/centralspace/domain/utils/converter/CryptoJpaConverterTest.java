package com.fenixcommunity.centralspace.domain.utils.converter;

import com.fenixcommunity.centralspace.utilities.common.Var;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(locations = {"classpath:security.properties"})
@ExtendWith(MockitoExtension.class)
public class CryptoJpaConverterTest {

    @ParameterizedTest
    @ValueSource(strings = {Var.PASSWORD_HIGH, Var.PASSWORD_LOW})
    void shouldConvertAsExpected(
            @ConvertWith(CryptoJpaConverterTestHelper.class) boolean excepted) {
        assertTrue(excepted);
    }
}
