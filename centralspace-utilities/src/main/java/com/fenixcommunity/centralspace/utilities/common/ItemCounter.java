package com.fenixcommunity.centralspace.utilities.common;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

public final class ItemCounter<T> {

    public Map<T, MutableInteger> countItemsFrequency(Collection<T> items) {
        final Map<T, MutableInteger> counterMap = new ConcurrentHashMap<>();
        items.forEach(item -> counterMap
                                        .compute(item, (k, v) -> v == null ? new MutableInteger(0) : v)
                                        .increment());
        return counterMap;
    }

    @Getter @AllArgsConstructor
    public static class MutableInteger {
        int count;

        public void increment() {
            this.count++;
        }
    }
}
