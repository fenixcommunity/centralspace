package com.fenixcommunity.centralspace.utilities.common;


import static com.fenixcommunity.centralspace.utilities.common.Var.CITY;
import static com.fenixcommunity.centralspace.utilities.common.Var.COUNTRY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class DevToolTest {

    @Test
    public void listFilesForDirectoryTest() {
        try {
            Set<String> filesForDirectory = FileDevTool.listFilesForDirectory("C:/projects/MK/centralspace-temp/");
            assertThat(filesForDirectory).isNotEmpty();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void deepCopyTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("json/the_same_account_1.json").getFile());
        SerializableObjToTest serializableObjToTest = new SerializableObjToTest(COUNTRY, CITY, file);

        SerializableObjToTest deepCopyResult = (SerializableObjToTest) DevTool.deepCopyOfObject(serializableObjToTest);
        deepCopyResult.setCountry("Dublin");

        assertNotEquals(
                serializableObjToTest.getCountry(),
                deepCopyResult.getCountry());
        assertEquals(
                serializableObjToTest.getCity(),
                deepCopyResult.getCity());
        assertEquals(
                serializableObjToTest.getFile(),
                deepCopyResult.getFile());
    }

    @Test
    public void generateSecureStringChainTest() {
        String resultWithSpecialChars = StringTool.generateSecurePassword();
        assertThat(resultWithSpecialChars).hasSize(10);

        String resultWithoutSpecialChars = StringTool.generateSecureToken();
        assertThat(resultWithoutSpecialChars).hasSize(38);
    }

    @Test
    public void countItemsFrequencyTest() {
        List<String> items = List.of( "China", "Australia", "India", "USA", "USSR", "UK", "China",
                "France", "Poland", "Austria", "India", "USA", "Egypt", "China");

        Map<String, ItemCounter.MutableInteger> stringMutableIntegerMap = StringTool.countItemsFrequency(items);

        assertEquals(3, stringMutableIntegerMap.get("China").getCount());
        assertEquals(2, stringMutableIntegerMap.get("India").getCount());
    }
}