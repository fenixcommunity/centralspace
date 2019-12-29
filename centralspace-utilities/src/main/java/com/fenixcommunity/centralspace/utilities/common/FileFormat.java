package com.fenixcommunity.centralspace.utilities.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum FileFormat {

    PDF("PDF", "pdf"),
    HTML("HTML", "html"),
    PNG("PNG", "png"),
    JPG("JPG", "jpg"),
    TXT("TXT", "txt"),
    DOCX("DOCX", "docx");

    private String formatName;
    private String subtype;

    public static FileFormat parseFileFormat(String subtype) {
        return EnumSet.allOf(FileFormat.class)
                .stream()
                .filter(e -> e.getSubtype().equals(subtype))
                .findFirst().orElse(null);
    }
}
