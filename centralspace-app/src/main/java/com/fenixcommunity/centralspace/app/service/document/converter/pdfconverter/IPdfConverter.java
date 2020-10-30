package com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter;

import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;

public interface IPdfConverter {
    void convertPdfToImage(FileFormat fileFormat);

    void convertImageToPdf(FileFormat fileFormat, RestCallerStrategy restCallerStrategy);

    void convertPdfToText();

    void convertTextToPdf();

    void convertPdfToDocx();
}