package com.fenixcommunity.centralspace.utilities.common;

import static lombok.AccessLevel.PRIVATE;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FilenameUtils;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FileDevTool {

    public static String getFileExtension(final String filename) {
        return FilenameUtils.getExtension(filename);
//   or return Files.getFileExtension(filename);
    }

    public static File createNewOutputFile(final String filePath) {
        if (createFileDirectories(filePath)) {
            return new File(filePath);
        }
        return null;
    }

    public static boolean createFileDirectories(final String filePath) {
        final Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static Set<String> listFilesForDirectory(final String dir) throws IOException {
        final FileVisitor visitor = new FileVisitor();
        Files.walkFileTree(Paths.get(dir), visitor);
        return visitor.getFileList();
    }

    public static boolean deleteFileContent(final String filePath) {
        try {
            FileChannel.open(Paths.get(filePath), StandardOpenOption.WRITE).truncate(0).close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
