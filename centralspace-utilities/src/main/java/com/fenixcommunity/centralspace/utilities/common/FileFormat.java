package com.fenixcommunity.centralspace.utilities.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum FileFormat {

    PDF("PDF", "pdf"),
    HTML("HTML", "html"),
    TXT("TXT", "txt"),
    DOCX("DOCX", "docx"),

    PNG("IMAGE", "png"),
    JPG("IMAGE", "jpg");

    private String categoryName;
    private String subtype;

    public static FileFormat parseFileFormat(final String subtype) {
        return EnumSet.allOf(FileFormat.class)
                .stream()
                .filter(e -> e.getSubtype().equals(subtype))
                .findFirst().orElse(null);
    }
}
