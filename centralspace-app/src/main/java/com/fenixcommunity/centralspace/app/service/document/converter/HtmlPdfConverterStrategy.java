package com.fenixcommunity.centralspace.app.service.document.converter;

public interface HtmlPdfConverterStrategy {
    void convertHtmlToPdf();

    void convertPdfToHtml();

    String getHtmlBody();
}