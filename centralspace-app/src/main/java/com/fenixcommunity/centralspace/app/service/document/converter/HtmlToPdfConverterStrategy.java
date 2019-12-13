package com.fenixcommunity.centralspace.app.service.document.converter;

public interface HtmlToPdfConverterStrategy {
    void convertHtmlToPdf();

    String getHtmlBody();
}