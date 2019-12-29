package com.fenixcommunity.centralspace.app.service.document.converter;

import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import org.springframework.web.client.RestTemplate;

public interface IPdfConverter {
    void convertPdfToImage(FileFormat fileFormat);

    void convertImageToPdf(FileFormat fileFormat, RestTemplate restTemplate);

    void convertPdfToText();

    void convertTextToPdf();

    void convertPdfToDocx();
}