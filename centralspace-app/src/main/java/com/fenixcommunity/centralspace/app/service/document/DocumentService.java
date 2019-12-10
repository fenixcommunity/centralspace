package com.fenixcommunity.centralspace.app.service.document;

import com.fenixcommunity.centralspace.utilities.configuration.properties.ResourceProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import static com.fenixcommunity.centralspace.app.service.document.DocumentConverterStrategyType.THYMELEAF;
import static java.util.Collections.singletonMap;

@Log4j2
@Service
public class DocumentService {
//todo to interface

    private final ResourceProperties resourceProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    public DocumentService(ResourceProperties resourceProperties) {
        this.resourceProperties = resourceProperties;
    }

    public void convertHtmlToPdf(String htmlFileName, DocumentConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            DocumentConverterStrategy converter = new ThymeleafDocumentConverter(htmlFileName, singletonMap("imageUrl", resourceProperties.getImageUrl()),templateEngine);
            converter.convertHtmlToPdf();
        }
    }

    public String getHtmlBody(String htmlFileName, DocumentConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            DocumentConverterStrategy converter = new ThymeleafDocumentConverter(htmlFileName, singletonMap("imageUrl", resourceProperties.getImageUrl()),templateEngine);
           return converter.getHtmlBody();
        }
        return "";
    }

}