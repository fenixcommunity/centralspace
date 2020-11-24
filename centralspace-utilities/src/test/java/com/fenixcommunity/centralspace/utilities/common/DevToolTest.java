package com.fenixcommunity.centralspace.utilities.common;


import static com.fenixcommunity.centralspace.utilities.common.Var.CITY;
import static com.fenixcommunity.centralspace.utilities.common.Var.COUNTRY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Test;

class DevToolTest {

    @Test
    public void listFilesForDirectoryTest() {
        try {
            Set<String> filesForDirectory = FileDevTool.listFilesForDirectory("C:/Projects/MK/centralspace-temp/");
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
}