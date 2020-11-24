package com.fenixcommunity.centralspace.utilities.web.file;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.io.FileMatchers.aFileNamed;
import static org.hamcrest.io.FileMatchers.aFileWithAbsolutePath;
import static org.hamcrest.io.FileMatchers.aFileWithSize;
import static org.hamcrest.io.FileMatchers.aReadableFile;
import static org.hamcrest.io.FileMatchers.aWritableFile;
import static org.hamcrest.io.FileMatchers.anExistingDirectory;
import static org.hamcrest.io.FileMatchers.anExistingFile;
import static org.hamcrest.io.FileMatchers.anExistingFileOrDirectory;

import java.io.File;

import com.fenixcommunity.centralspace.utilities.test.ReplaceCamelCaseAndUnderscore;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceCamelCaseAndUnderscore.class)
public class FileTest {

    @Test
    void givenJsonFiles_thenTestFileDetails() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("json/the_same_account_1.json").getFile());
        File dir = new File(classLoader.getResource("json/").getFile());

        assertThat(file, anExistingFile());
        assertThat(dir, anExistingDirectory());
        assertThat(file, anExistingFileOrDirectory());
        assertThat(dir, anExistingFileOrDirectory());
        assertThat(file, aFileNamed(equalToIgnoringCase("the_same_account_1.json")));
        assertThat(file, aFileWithAbsolutePath(containsString("json\\")));
        assertThat(file, aFileWithSize(greaterThan(1L)));
        assertThat(file, aReadableFile());
        assertThat(file, aWritableFile());
    }

}