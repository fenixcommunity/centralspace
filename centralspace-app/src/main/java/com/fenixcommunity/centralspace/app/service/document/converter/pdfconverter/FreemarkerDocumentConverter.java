package com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
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