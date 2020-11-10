package com.fenixcommunity.centralspace.utilities.common;

import static lombok.AccessLevel.PRIVATE;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FileVisitor extends SimpleFileVisitor<Path> {
    private final Set<String> fileList;

    public FileVisitor() {
        super();
        fileList = new HashSet<>();
    }

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
        if (!Files.isDirectory(file)) {
            fileList.add(file.getFileName().toString());
        }
        return FileVisitResult.CONTINUE;
    }

    public Set<String> getFileList() {
        return fileList;
    }
}
