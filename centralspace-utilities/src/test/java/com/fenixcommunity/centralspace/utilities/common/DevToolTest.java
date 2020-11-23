package com.fenixcommunity.centralspace.utilities.common;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

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
}