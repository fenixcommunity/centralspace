package com.fenixcommunity.centralspace.utilities.common;

import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DevTool {

    public static final int METHOD_INVOCATION_TIME_LIMIT_mS = 100;

    public static String getClassPath(Class clazz) {
        Class unwrappedClass = ClassUtils.getUserClass(clazz);
        return unwrappedClass.getName();
    }
    public static File createNewOutputFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            return null;
        }
        return new File(filePath);
    }
}