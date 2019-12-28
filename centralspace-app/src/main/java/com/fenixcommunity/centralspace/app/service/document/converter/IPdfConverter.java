package com.fenixcommunity.centralspace.app.service.document.converter;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public interface IPdfConverter {
    void convertPdfToImage(MediaType extension);

    void convertImageToPdf(MediaType extension, RestTemplate restTemplate);

    void convertPdfToText();

    void convertTextToPdf();

    void convertPdfToDocx();
}