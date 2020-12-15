package com.fenixcommunity.centralspace.utilities.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

class CollectionToolTest {

    @Test
    public void getSameItemsTest() {
        Collection<Integer> result = CollectionTool.getSameItems(List.of(1, 2, 3, 4), List.of(2, 3, 5));
        assertThat(result).containsAll(List.of(2,3));
    }

    @Test
    public void getDifferentItemsTest() {
        Collection<Integer> result = CollectionTool.getDifferentItems(List.of(1, 2, 3, 4), List.of(2, 3, 5));
        assertThat(result).containsAll(List.of(1,4));
    }

}