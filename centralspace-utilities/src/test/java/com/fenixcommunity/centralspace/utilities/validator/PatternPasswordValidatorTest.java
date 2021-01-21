package com.fenixcommunity.centralspace.utilities.validator;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.springframework.util.Assert.doesNotContain;
import static org.springframework.util.Assert.hasLength;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isAssignable;
import static org.springframework.util.Assert.isInstanceOf;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.noNullElements;
import static org.springframework.util.Assert.notEmpty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import com.fenixcommunity.centralspace.utilities.common.Var;
import com.fenixcommunity.centralspace.utilities.test.ReplaceCamelCaseAndUnderscore;
import com.fenixcommunity.centralspace.utilities.time.TimeTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Test to check PasswordValidator")
@DisplayNameGeneration(ReplaceCamelCaseAndUnderscore.class)
@ExtendWith(MockitoExtension.class)
class PatternPasswordValidatorTest {

    @InjectMocks
    private ValidatorFactory validatorFactory;

    @Spy
    private ValidatorFactory validatorFactorySpy;

    @InjectMocks
    private TimeTool timeTool;

    @BeforeEach
    public void setUp() {
        timeTool = new TimeTool(validatorFactory);
    }

    @Nested
    class whenValidatorIsValid {
        @DisplayName("We have correct password and then true")
        @ParameterizedTest(name = "For example, password {0} is correct.")
        @ValueSource(strings = {Var.PASSWORD_HIGH, "Password1&23"})
        public void givenCorrectHighPassword_thenValidate(String password) {
            // given
            Validator validator = validatorFactory.getInstance(ValidatorType.PASSWORD_HIGH);
            // when
            boolean result = validator.isValid(password);
            // then
            isTrue(password.length() > 1, "password must longer than 1");
            isAssignable(Validator.class, PatternPasswordValidator.class);
            isInstanceOf(PatternPasswordValidator.class, validator);
            hasLength(password, "password must not be null and must not the empty");
            hasText(password, "key must not be null and must contain at least one non-whitespace  character");
            doesNotContain(password, "123", "key mustn't contain 123");
            List<String> list = new ArrayList<>(Arrays.asList(password, password, password));
            notEmpty(list, "collection mustn't be empty");
            noNullElements(list, "collection  mustn't contain null elements");

            assertTrue(result);
        }

        @ParameterizedTest(name = "For parameter {0}")
        @ValueSource(strings = {"", Var.PASSWORD_LOW, "  ", "password1232132 343"})
        public void givenNotCorrectHighPassword_thenNotValidate(String password) {
            // given
            Validator validator = validatorFactory.getInstance(ValidatorType.PASSWORD_HIGH);
            // when
            boolean result = validator.isValid(password);
            // then
            assertFalse(result);
        }

        @ParameterizedTest
        @NullSource
        public void givenNullPassword_thenNotValidate(String password) {
            // given
            Validator validator = validatorFactory.getInstance(ValidatorType.PASSWORD_HIGH);
            // when
            boolean result = validator.isValid(password);
            // then
            assertFalse(result);
        }
    }

    // todo przenies
    @ParameterizedTest(name = "{index} {0} is 30 days long")
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    public void givenSomeMonths_thenHave30DaysLong(Month month) {
        final boolean isALeapYear = false;
        EnumSet<Month> enumSet = EnumSet.of(Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER);
        assertTrue(enumSet.contains(month));
        assertEquals(30, month.length(isALeapYear));
    }

    @ParameterizedTest
    @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
        // @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void givenToLowerCase_thenGenerateTheExpectedLowercaseValue(String input, String expected) {
        String actualValue = input.toLowerCase();
        assertEquals(expected, actualValue);
    }

    @ParameterizedTest
    @MethodSource("provideTimeArguments")
    public void givenTimeToolIsEqualMethod_thenBeCorrect(ZonedDateTime time1, ZonedDateTime time2, boolean expected) {
        assertEquals(expected, timeTool.isEqual(time1, time2));
    }

    public static Stream<Arguments> provideTimeArguments() {
        ZoneId zoneId1 = ZoneId.of("UTC+1");
        ZonedDateTime time1 = ZonedDateTime.of(
                LocalDate.of(2019, 3, 12),
                LocalTime.of(12, 44),
                zoneId1);

        ZoneId zoneId2 = ZoneId.of("UTC+2");
        ZonedDateTime time2 = ZonedDateTime.of(
                LocalDate.of(2019, 3, 12),
                LocalTime.of(13, 44),
                zoneId2);

        // return Stream.of(null, "", "  ");
        return Stream.of(
                Arguments.of(time1, time2, true),
                Arguments.of(time1, null, false)
        );
    }

    @Test
    public void givenExampleAndLambdaInvocation_thenDoNothing() {
        doNothing().when(validatorFactorySpy).validateAllowedValidatorTypes(null);
        assertThrows(IllegalArgumentException.class, () -> {
            validatorFactorySpy.getInstance(null);
        });
        // would work fine
        // doReturn("test").when(myClass).anotherMethodInClass();

        // would throw a NullPointerException
        // when(myClass.anotherMethodInClass()).thenReturn("test");
    }

}
