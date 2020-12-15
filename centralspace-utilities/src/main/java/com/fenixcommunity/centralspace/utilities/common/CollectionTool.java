package com.fenixcommunity.centralspace.utilities.common;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;

public class CollectionTool {
    public static <T> boolean addIgnoringNull(final Collection<T> collection, final T obj) {
        return CollectionUtils.addIgnoreNull(collection, obj);
    }

    public static <T> Collection<T> removeNullValues(Collection<T> collection) {
        CollectionUtils.filter(collection, Objects::nonNull);
        return collection;
    }

    public static <T> Collection<T> getSameItems(final Collection<T> collection1, final Collection<T> collection2) {
        return CollectionUtils.intersection(collection1, collection2);
    }

    public static <T> Collection<T> getDifferentItems(final Collection<T> collection1, final Collection<T> collection2) {
        return CollectionUtils.subtract(collection1, collection2);
    }

    public static <T extends Comparable> List<T> mergeSortedListsWithoutDuplicates(final List<T> sortedList1, final List<T> sortedList2) {
        return CollectionUtils.collate(sortedList1, sortedList2);
    }

    public static String[] mergeStringArrays(final String[]... arrays) {
        String[] result = new String[0];
        if (Objects.nonNull(arrays) && Stream.of(arrays).noneMatch(Objects::isNull)) {
            return Stream.of(arrays).flatMap(Stream::of).toArray(String[]::new);
        }
        return result;
    }

    public static List<String> combineToStringList(final List<Object> list1, final List<Object> list2, final String separator) {
        return IntStream
                .range(0, Math.min(list1.size(), list2.size()))
                .mapToObj(i -> list1.get(i) + separator + list2.get(i))
                .collect(Collectors.toList());
    }
}
