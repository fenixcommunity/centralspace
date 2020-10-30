package com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter;

public interface HtmlPdfConverterStrategy {
    void convertHtmlToPdf();

    void convertPdfToHtml();

    String getHtmlBody();
}