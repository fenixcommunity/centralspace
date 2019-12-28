package com.fenixcommunity.centralspace.utilities.common;

import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class DevTool {

    public static final int METHOD_INVOCATION_TIME_LIMIT_mS = 100;

    public static String getClassPath(Class clazz) {
        Class unwrappedClass = ClassUtils.getUserClass(clazz);
        return unwrappedClass.getName();
    }

    public static String getSimpleClassName(Class clazz) {
        return clazz.getSimpleName();
    }

    public static String getClassName(Class clazz) {
        return clazz.getName();
    }

    public static File createNewOutputFile(String filePath) {
        if (createFileDirectories(filePath)) {
            return new File(filePath);
        }
        return null;
    }

    public static boolean createFileDirectories(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    //todo generic
    public static String[] mergeStringArrays(String[]... arrays) {
        String[] result = new String[0];
        if (Objects.nonNull(arrays) && Stream.of(arrays).noneMatch(Objects::isNull)) {
            return Stream.of(arrays).flatMap(Stream::of).toArray(String[]::new);
        }
        return result;
    }

    public static String[] listsTo1Array(List<String>... lists) {
        return mergeLists(lists).toArray(String[]::new);
    }

    public static List<String> mergeLists(List<String>... lists) {
        List<String> result = new ArrayList<>();
        Arrays.stream(lists).forEach(result::addAll);
        return result;
    }

}