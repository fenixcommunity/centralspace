package com.fenixcommunity.centralspace.utilities.common;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

import java.util.EnumSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;

@AllArgsConstructor @Getter
public enum FileFormat {

    PDF("PDF", "pdf"),
    CSV("CSV", "csv"),
    JSON("JSON", "json"),
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

    public static FileFormat getFileFormatByMediaType(final MediaType mediaType) {
        switch (mediaType.getType()) {
            case APPLICATION_PDF_VALUE:
                return PDF;
            default:
                return null;
        }
    }
}
