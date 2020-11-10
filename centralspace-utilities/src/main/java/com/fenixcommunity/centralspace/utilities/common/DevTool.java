package com.fenixcommunity.centralspace.utilities.common;

import static java.util.Collections.unmodifiableList;
import static lombok.AccessLevel.PRIVATE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public static File createNewOutputFile(final String filePath) {
        if (createFileDirectories(filePath)) {
            return new File(filePath);
        }
        return null;
    }

    public static boolean createFileDirectories(final String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static Set<String> listFilesForDirectory(final String dir) throws IOException {
        final FileVisitor visitor = new FileVisitor();
        Files.walkFileTree(Paths.get(dir), visitor);
        return visitor.getFileList();
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


    public static boolean isJSONValid(final String jsonInString) {
        try {
            new ObjectMapper().readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}