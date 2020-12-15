package com.fenixcommunity.centralspace.utilities.common;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.text.WordUtils;
import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.apache.commons.text.similarity.LongestCommonSubsequenceDistance;
import org.apache.commons.text.translate.UnicodeEscaper;

public class StringTool {

    public static Map<String, ItemCounter.MutableInteger> countItemsFrequency(final List<String> items) {
        ItemCounter<String> itemCounter = new ItemCounter<>();
        return itemCounter.countItemsFrequency(items);
    }

    public static String generateSecureToken() {
        return generateSecureStringChain(38, false);
    }

    public static String generateSecurePassword() {
        return generateSecureStringChain(10, true);
    }

    private static String generateSecureStringChain(int length, boolean includeSpecialChars) {
        int eachElementCount = (length / 5);
        int bonusCount = (length % 5) + (includeSpecialChars ? 0 : eachElementCount);
        final String upperCaseLetters = RandomStringUtils.random(eachElementCount, 65, 90, true, true);
        final String lowerCaseLetters = RandomStringUtils.random(eachElementCount, 97, 122, true, true);
        final String numbers = RandomStringUtils.randomNumeric(eachElementCount);
        final String specialChar = includeSpecialChars ? RandomStringUtils.random(eachElementCount, 33, 47, false, false) : "";
        final String totalChars = RandomStringUtils.randomAlphanumeric(eachElementCount + bonusCount);

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

    public static boolean checkStringContainsWords(final String inputString, final String[] words) {
        return WordUtils.containsAllWords(inputString, words); // or List.of(inputString.split(SPACE)).containsAll(List.of(words))
    }

    public static String capitalize(final String toBeCapitalized) {
        return WordUtils.capitalize(toBeCapitalized);
    }

    public static String substituteTemplateString(final String template, final Map<String, String> substitutes) {
        return new StrSubstitutor(substitutes).replace(template);
    }

    public static int getSimilaritiesOfStrings(final String string1, final String string2) {
        return new LongestCommonSubsequence().apply(string1, string2);
    }

    public static int getDistancesOfStrings(final String string1, final String string2) {
        return new LongestCommonSubsequenceDistance().apply(string1, string2);
    }

    public static String toUnicode(final String string) {
        return UnicodeEscaper.above(0).translate(string);
    }

}
