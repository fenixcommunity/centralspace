package com.fenixcommunity.centralspace.utilities.test;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator;

public class ReplaceCamelCaseAndUnderscore extends DisplayNameGenerator.Standard {
    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return replaceCamelCaseAndUnderscore(super.generateDisplayNameForClass(testClass));
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return replaceCamelCaseAndUnderscore(super.generateDisplayNameForNestedClass(nestedClass));
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return this.replaceCamelCaseAndUnderscore(testMethod.getName()) +
                DisplayNameGenerator.parameterTypesAsString(testMethod);
    }

    private String replaceCamelCaseAndUnderscore(final String testName) {
        final StringBuilder result = new StringBuilder();
        result.append(testName.charAt(0));
        for (int i = 1; i < testName.length(); i++) {
            final char testNameChar = testName.charAt(i);
            if (Character.isUpperCase(testNameChar)) {
                result.append(' ');
                result.append(Character.toLowerCase(testNameChar));
            } else if (testNameChar == '_') {
                result.append(" --> ");
            } else {
                result.append(testNameChar);
            }
        }
        return result.toString();
    }
}