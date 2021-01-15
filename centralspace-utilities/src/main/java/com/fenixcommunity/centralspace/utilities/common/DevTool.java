package com.fenixcommunity.centralspace.utilities.common;

import static com.fenixcommunity.centralspace.utilities.common.Var.SPACE;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SerializationUtils;
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

    public static Object deepCopyOfObject(final Serializable serializableObj) {
        return SerializationUtils.clone(serializableObj);
    }

    public static String[] listsTo1Array(final List<String>... lists) {
        return mergeLists(lists).toArray(String[]::new);
    }

    public static <T> List<T> listsTo1List(final List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
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
}