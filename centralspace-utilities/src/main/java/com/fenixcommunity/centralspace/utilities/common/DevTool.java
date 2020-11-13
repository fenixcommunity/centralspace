package com.fenixcommunity.centralspace.utilities.common;

import static com.fenixcommunity.centralspace.utilities.common.Var.SPACE;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PRIVATE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.FieldDefaults;
import org.springframework.util.ClassUtils;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DevTool {
    public static final int METHOD_INVOCATION_TIME_LIMIT_mS = 100;

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
        return unmodifiableList(result);
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

    public static File createNewOutputFile(final String filePath) {
        return FileDevTool.createNewOutputFile(filePath);
    }

    public static boolean createFileDirectories(final String filePath) {
        return FileDevTool.createFileDirectories(filePath);
    }

    public static Set<String> listFilesForDirectory(final String dir) throws IOException {
        return FileDevTool.listFilesForDirectory(dir);
    }

    public static boolean deleteFileContent(final String filePath) {
        return FileDevTool.deleteFileContent(filePath);
    }

}