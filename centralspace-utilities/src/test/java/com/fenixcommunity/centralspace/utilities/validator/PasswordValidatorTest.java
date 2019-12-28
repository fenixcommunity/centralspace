package com.fenixcommunity.centralspace.utilities.validator;


import com.fenixcommunity.centralspace.utilities.common.Var;
import com.fenixcommunity.centralspace.utilities.time.TimeTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.util.Assert.*;

@DisplayName("Test to check PasswordValidator")
@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTest {

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

    @DisplayName("We have correct password and should be true")
    @ParameterizedTest(name = "For example, password {0} is correct.")
    @ValueSource(strings = {Var.PASSWORD_HIGH, "Password1&23"})
    public void shouldValidate_CorrectHighPassword(String password) {
        // given
        Validator validator = validatorFactory.getInstance(ValidatorType.PASSWORD_HIGH);
        // when
        boolean result = validator.isValid(password);
        // then
        isTrue(password.length() > 1, "password must longer than 1");
        isAssignable(Validator.class, PasswordValidator.class);
        isInstanceOf(PasswordValidator.class, validator);
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
    public void shouldNotValidate_NotCorrectHighPassword(String password) {
        // given
        Validator validator = validatorFactory.getInstance(ValidatorType.PASSWORD_HIGH);
        // when
        boolean result = validator.isValid(password);
        // then
        assertFalse(result);
    }

    @ParameterizedTest
    @NullSource
    public void shouldNotValidate_NullPassword(String password) {
        // given
        Validator validator = validatorFactory.getInstance(ValidatorType.PASSWORD_HIGH);
        // when
        boolean result = validator.isValid(password);
        // then
        assertFalse(result);
    }

    // todo przenies
    @ParameterizedTest(name = "{index} {0} is 30 days long")
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    public void shouldHave30DaysLong_SomeMonths(Month month) {
        final boolean isALeapYear = false;
        EnumSet<Month> enumSet = EnumSet.of(Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER);
        assertTrue(enumSet.contains(month));
        assertEquals(30, month.length(isALeapYear));
    }

    @ParameterizedTest
    @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
        // @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldGenerateTheExpectedLowercaseValue_toLowerCase(String input, String expected) {
        String actualValue = input.toLowerCase();
        assertEquals(expected, actualValue);
    }

    @ParameterizedTest
    @MethodSource("provideTimeArguments")
    public void shouldBeCorrect_TimeToolIsEqualMethod(ZonedDateTime time1, ZonedDateTime time2, boolean expected) {
        assertEquals(expected, timeTool.IS_EQUAL(time1, time2));
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
    public void shouldDoNothing_ExampleAndLambdaInvocation() {
        doNothing().when(validatorFactorySpy).validateInstanceExist(null);
        assertThrows(NullPointerException.class, () -> {
            validatorFactorySpy.getInstance(null);
        });
        // would work fine
        // doReturn("test").when(myClass).anotherMethodInClass();

        // would throw a NullPointerException
        // when(myClass.anotherMethodInClass()).thenReturn("test");
    }

}
