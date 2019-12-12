package com.fenixcommunity.centralspace.app.service.document;

import com.fenixcommunity.centralspace.app.service.document.converter.HtmlToPdfConverterStrategy;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FreemarkerDocumentConverter implements HtmlToPdfConverterStrategy {

    @Override
    public void convertHtmlToPdf() {
//todo implement
    }

    @Override
    public String getHtmlBody() {
        return null;
    }

}