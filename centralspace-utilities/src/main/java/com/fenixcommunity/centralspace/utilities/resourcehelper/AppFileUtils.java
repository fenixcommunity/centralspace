package com.fenixcommunity.centralspace.utilities.resourcehelper;

import java.io.IOException;
import java.nio.charset.Charset;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

@Log4j2
public class AppFileUtils {

    public static String loadFile(final String path) {
        try {
            return getResourceAsString(path);
        } catch (IOException e) {
            log.error("Problem with finding the file with path: " + path);
            return null;
        }
    }

    private static String getResourceAsString(final String filePath) throws IOException {
        final var input = AppFileUtils.class.getClassLoader().getResourceAsStream(filePath);
        return IOUtils.toString(input, Charset.defaultCharset());
    }
}
