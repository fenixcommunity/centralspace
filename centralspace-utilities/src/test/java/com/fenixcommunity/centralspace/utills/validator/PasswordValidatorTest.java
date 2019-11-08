package com.fenixcommunity.centralspace.utills.validator;


import com.fenixcommunity.centralspace.utills.common.test.Var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Test to check PasswordValidator")
@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTest {
//TODO https://www.baeldung.com/parameterized-tests-junit-5
//TODO https://www.baeldung.com/mockito-junit-5-extension

    @InjectMocks
    private ValidatorFactory validatorFactory;

    @DisplayName("We have correct password and should be true")
    @ParameterizedTest(name = "For example, password {0} is correct.")
    @ValueSource(strings = { Var.PASSWORD_HIGH, "Password1&23" })
    public void shouldValidate_CorrectHighPassword(String password) {
        // given
        Validator validator =  validatorFactory.getInstance(ValidatorType.PASSWORD_HIGH);
        // when
        boolean result = validator.isValid(password);
        // then
        assertTrue(result);
    }

    @ParameterizedTest(name = "For parameter {0}")
    @ValueSource(strings = { "", Var.PASSWORD_LOW, "  ", "password1232132 343" })
    public void shouldNotValidate_NotCorrectHighPassword(String password) {
        // given
        Validator validator =  PasswordValidator.highValidator();
        // when
        boolean result = validator.isValid(password);
        // then
        assertFalse(result);
    }

    @ParameterizedTest
    @NullSource
    public void shouldNotValidate_NullPassword(String password) {
        // given
        Validator validator =  PasswordValidator.highValidator();
        // when
        boolean result = validator.isValid(password);
        // then
        assertFalse(result);
    }

    // todo przenies
    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    public void shouldSomeMonths_Have30DaysLong(Month month){
        final boolean isALeapYear = false;
        assertEquals(30, month.length(isALeapYear));
    }

    @ParameterizedTest
    @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
        // @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void toLowerCase_ShouldGenerateTheExpectedLowercaseValue(String input, String expected) {
        String actualValue = input.toLowerCase();
        assertEquals(expected, actualValue);
    }

}
