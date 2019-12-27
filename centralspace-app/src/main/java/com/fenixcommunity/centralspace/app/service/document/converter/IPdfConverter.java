package com.fenixcommunity.centralspace.app.service.document.converter;

import org.springframework.http.MediaType;

public interface IPdfConverter {
    void convertPdfToText();

    void convertTextToPdf();

    void convertPdfToDocx();

    void convertPdfToImage(MediaType extension);

    void convertImageToPdf();

}