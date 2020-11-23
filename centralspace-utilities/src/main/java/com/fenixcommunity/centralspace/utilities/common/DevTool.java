package com.fenixcommunity.centralspace.utilities.common;

import static com.fenixcommunity.centralspace.utilities.common.Var.SPACE;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.ClassUtils;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DevTool {
    public static final int METHOD_INVOCATION_TIME_LIMIT_mS = 100;

    //todo add JavaPoet - API to generate Java source code (only new methods, classes)
    // and Module java 9 API

    public static String getClassPath(final Class clazz) {
        final Class unwrappedClass = ClassUtils.getUserClass(clazz);
        return unwrappedClass.getName();
    }

    public static String getSimpleClassName(final Class clazz) {
        return clazz.getSimpleName();
    }

    public static String getClassName(final Class clazz) {
        return clazz.getName();
    }

    //todo generic
    public static String[] mergeStringArrays(final String[]... arrays) {
        String[] result = new String[0];
        if (Objects.nonNull(arrays) && Stream.of(arrays).noneMatch(Objects::isNull)) {
            return Stream.of(arrays).flatMap(Stream::of).toArray(String[]::new);
        }
        return result;
    }

    public static String[] listsTo1Array(final List<String>... lists) {
        return mergeLists(lists).toArray(String[]::new);
    }

    public static List<String> mergeLists(final List<String>... lists) {
        final List<String> result = new ArrayList<>();
        Arrays.stream(lists).forEach(result::addAll);
        return List.copyOf(result); // or unmodifiableList
    }

    public static Map<String, String> mapStringToMap(String mapAsString) {
        return Arrays.stream(mapAsString.split(","))
                .map(entry -> entry.split("="))
                .collect(toMap(entry -> entry[0], entry -> entry[1]));
    }

    public static boolean isJSONValid(final String jsonInString) {
        try {
            new ObjectMapper().readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean checkStringContainsWords(final String inputString, final String[] words) {
        return List.of(inputString.split(SPACE)).containsAll(List.of(words));
    }

    public static String generateSecurePassword() {
        final String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        final String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        final String numbers = RandomStringUtils.randomNumeric(2);
        final String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        final String totalChars = RandomStringUtils.randomAlphanumeric(2);

        final String combinedChars = upperCaseLetters
                .concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar).concat(totalChars);

        final List<Character> charsList = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(charsList);

        return charsList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}