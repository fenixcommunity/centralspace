package com.fenixcommunity.centralspace.app.service.document;

import com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategy;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FreemarkerDocumentConverter implements HtmlPdfConverterStrategy {

    @Override
    public void convertHtmlToPdf() {
//todo implement
    }

    @Override
    public void convertPdfToHtml() {

    }

    @Override
    public String getHtmlBody() {
        return null;
    }

}