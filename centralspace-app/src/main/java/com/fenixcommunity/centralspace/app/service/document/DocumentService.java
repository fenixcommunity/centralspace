package com.fenixcommunity.centralspace.app.service.document;

import com.fenixcommunity.centralspace.app.service.SecurityService;
import com.fenixcommunity.centralspace.app.service.document.converter.BasicPdfConverter;
import com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategy;
import com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategyType;
import com.fenixcommunity.centralspace.app.service.document.converter.IPdfConverter;
import com.fenixcommunity.centralspace.app.service.document.converter.ThymeleafPdfConverter;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;

import java.util.Map;

import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategyType.BASIC;
import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategyType.THYMELEAF;
import static java.util.Collections.singletonMap;

@Log4j2
@Service
public class DocumentService {
//todo to interface

    @Autowired
    private ResourceLoaderTool resourceTool;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private SecurityService securityService;

    private final RestTemplateBuilder restTemplateBuilder;

    public DocumentService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public void convertPdfToImage(String pdfFileName, FileFormat fileFormat) {
        IPdfConverter converter = new BasicPdfConverter(pdfFileName, resourceTool);
        converter.convertPdfToImage(fileFormat);
    }

    public void convertImageToPdfAsAdmin(String imageFileName, FileFormat fileFormat) {
        IPdfConverter converter = new BasicPdfConverter(imageFileName, resourceTool);
        if (securityService.isValidSecurityRole()) {
            RestTemplate restTemplate = restTemplateBuilder.build();
            converter.convertImageToPdf(fileFormat, restTemplate);
        }
    }

    public void convertHtmlToPdf(String htmlFileName, HtmlPdfConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            Map<String, String> thymeleafVariables = singletonMap("imageUrl", resourceTool.getResourceProperties().getImageUrl());
            HtmlPdfConverterStrategy converter = new ThymeleafPdfConverter(htmlFileName, thymeleafVariables, templateEngine, resourceTool);
            converter.convertHtmlToPdf();
        } else if (BASIC == strategyType) {
            HtmlPdfConverterStrategy converter = new BasicPdfConverter(htmlFileName, resourceTool);
            converter.convertHtmlToPdf();
        }
    }

    public void convertPdfToHtml(String pdfFileName, HtmlPdfConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            HtmlPdfConverterStrategy converter = new ThymeleafPdfConverter(pdfFileName, null, templateEngine, resourceTool);
            converter.convertPdfToHtml();
        } else if (BASIC == strategyType) {
            HtmlPdfConverterStrategy converter = new BasicPdfConverter(pdfFileName, resourceTool);
            converter.convertPdfToHtml();
        }
    }

    public String getHtmlBody(String htmlFileName, HtmlPdfConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            Map<String, String> thymeleafVariables = singletonMap("imageUrl", resourceTool.getResourceProperties().getImageUrl());
            HtmlPdfConverterStrategy converter = new ThymeleafPdfConverter(htmlFileName, thymeleafVariables, templateEngine, resourceTool);
            return converter.getHtmlBody();
        } else if (BASIC == strategyType) {
            HtmlPdfConverterStrategy converter = new BasicPdfConverter(htmlFileName, resourceTool);
            return converter.getHtmlBody();
        }
        return "";
    }

    public void convertPdfToText(String pdfFileName) {
        IPdfConverter converter = new BasicPdfConverter(pdfFileName, resourceTool);
        converter.convertPdfToText();
    }

    public void convertTextToPdf(String textFileName) {
        IPdfConverter converter = new BasicPdfConverter(textFileName, resourceTool);
        converter.convertTextToPdf();
    }

    public void convertPdfToDocx(String pdfFileName) {
        IPdfConverter converter = new BasicPdfConverter(pdfFileName, resourceTool);
        converter.convertPdfToDocx();
    }

}