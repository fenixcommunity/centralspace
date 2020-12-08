package com.fenixcommunity.centralspace.utilities.common;

import static lombok.AccessLevel.PRIVATE;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

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

    public static byte[] getBytesFromFile(final File file) {
        if (file != null && file.length() > 0) {
            try {
                return IOUtils.toByteArray(FileUtils.openInputStream(file));
            } catch (IOException e) {
                return new byte[0];
            }
        }

        return new byte[0];
    }

    public static File copyFile(final File originalFile, final String newFilePath) {
        final File copiedFile = new File(newFilePath);
        try {
            FileUtils.copyFile(originalFile, copiedFile);
        } catch (IOException e) {
            return null;
        }
        return copiedFile;
    }

    public static File appendStringToFile(final File file, final String stringToAppend) {
        try {
            FileUtils.writeStringToFile(file, stringToAppend, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            return file;
        }
        return file;
    }

}
