package com.fenixcommunity.centralspace.domain.converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fenixcommunity.centralspace.utilities.common.Var;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = {"classpath:security.yml"})
@ExtendWith(MockitoExtension.class)
class CryptoJpaConverterTest {

    @ParameterizedTest
    @ValueSource(strings = {Var.PESEL, Var.NIP})
    void shouldConvertAsExpected(
            @ConvertWith(CryptoJpaConverterTestHelper.class) boolean excepted) {
        assertTrue(excepted);
    }
}
