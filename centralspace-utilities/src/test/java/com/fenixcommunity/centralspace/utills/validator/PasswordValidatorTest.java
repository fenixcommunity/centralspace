package com.fenixcommunity.centralspace.utills.validator;


import com.fenixcommunity.centralspace.utills.common.test.Var;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
@DisplayName("Test to check PasswordValidator")
public class PasswordValidatorTest {
//TODO test

//    @Mock
//    private ValidatorFactory validatorFactory;

    @Test
    @DisplayName("We have correct password and should be true")
//    @ParameterizedTest(name = "For example, password {0} is correct.")
//    @ValueSource(strings = { Var.PASSWORD_HIGH, "Password1&23" })
    public void shouldValidateInCorrectHighPassword(String password) {

        // given
        Validator validator =  PasswordValidator.highValidator();

        // when
        boolean result = validator.isValid(password);

        // then
        assertTrue(result);
    }

}
