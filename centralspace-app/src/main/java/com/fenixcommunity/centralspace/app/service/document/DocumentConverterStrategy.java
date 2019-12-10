package com.fenixcommunity.centralspace.app.service.document;

public interface DocumentConverterStrategy {
    void convertHtmlToPdf();
    String getHtmlBody();
}