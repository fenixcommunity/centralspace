package com.fenixcommunity.centralspace.utilities.common;

import org.springframework.util.ClassUtils;

public class DevTool {

    public static final int METHOD_INVOCATION_TIME_LIMIT_mS = 100;

    public static String getClassPath(Class clazz) {
        Class unwrappedClass = ClassUtils.getUserClass(clazz);
        return unwrappedClass.getName();
    }
}