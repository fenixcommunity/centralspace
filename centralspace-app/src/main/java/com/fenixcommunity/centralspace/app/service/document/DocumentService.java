package com.fenixcommunity.centralspace.app.service.document;

import static com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.HtmlPdfConverterStrategyType.BASIC;
import static com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.HtmlPdfConverterStrategyType.THYMELEAF;
import static java.util.Collections.singletonMap;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.util.Map;

import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.service.document.converter.jsonconverter.FromJsonConverter;
import com.fenixcommunity.centralspace.app.service.document.converter.jsonconverter.ToJsonConverter;
import com.fenixcommunity.centralspace.app.service.security.SecurityService;
import com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.BasicPdfConverter;
import com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.HtmlPdfConverterStrategy;
import com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.HtmlPdfConverterStrategyType;
import com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.IPdfConverter;
import com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.ThymeleafPdfConverter;
import com.fenixcommunity.centralspace.app.service.document.pdfcreator.IPdfCreator;
import com.fenixcommunity.centralspace.app.service.document.pdfcreator.ITextPdfCreator;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Log4j2
@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class DocumentService {
//todo to interface

    private final ResourceLoaderTool resourceTool;
    private final TemplateEngine templateEngine;
    private final SecurityService securityService;

    private final RestCallerStrategy restCallerStrategy;

    public void createPdf(final String pdfFileName) {
        final IPdfCreator pdfCreator = new ITextPdfCreator(pdfFileName, resourceTool);
        pdfCreator.createPdf();
    }

    public void convertPdfToImage(final String pdfFileName, final FileFormat fileFormat) {
        final IPdfConverter converter = new BasicPdfConverter(pdfFileName, resourceTool);
        converter.convertPdfToImage(fileFormat);
    }

    public void convertImageToPdfAsAdminByWebClientAndRestTemplate(final String imageFileName, final FileFormat fileFormat) {
        final IPdfConverter converter = new BasicPdfConverter(imageFileName, resourceTool);
        if (securityService.isValidSecurityRole()) {
            converter.convertImageToPdf(fileFormat, restCallerStrategy);
        }
    }

    public void convertHtmlToPdf(final String htmlFileName, final HtmlPdfConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            final Map<String, String> thymeleafVariables = singletonMap("imageUrl",  resourceTool.getAbsoluteImagePath());
            final HtmlPdfConverterStrategy converter = new ThymeleafPdfConverter(htmlFileName, thymeleafVariables, templateEngine, resourceTool);
            converter.convertHtmlToPdf();
        } else if (BASIC == strategyType) {
            final HtmlPdfConverterStrategy converter = new BasicPdfConverter(htmlFileName, resourceTool);
            converter.convertHtmlToPdf();
        }
    }

    public void convertPdfToHtml(final String pdfFileName, final HtmlPdfConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            final HtmlPdfConverterStrategy converter = new ThymeleafPdfConverter(pdfFileName, null, templateEngine, resourceTool);
            converter.convertPdfToHtml();
        } else if (BASIC == strategyType) {
            final HtmlPdfConverterStrategy converter = new BasicPdfConverter(pdfFileName, resourceTool);
            converter.convertPdfToHtml();
        }
    }

    public String getHtmlBody(final String htmlFileName, final HtmlPdfConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            final Map<String, String> thymeleafVariables = singletonMap("imageUrl", resourceTool.getAbsoluteImagePath());
            final HtmlPdfConverterStrategy converter = new ThymeleafPdfConverter(htmlFileName, thymeleafVariables, templateEngine, resourceTool);
            return converter.getHtmlBody();
        } else if (BASIC == strategyType) {
            final HtmlPdfConverterStrategy converter = new BasicPdfConverter(htmlFileName, resourceTool);
            return converter.getHtmlBody();
        }
        return "";
    }

    public void convertPdfToText(final String pdfFileName) {
        final IPdfConverter converter = new BasicPdfConverter(pdfFileName, resourceTool);
        converter.convertPdfToText();
    }

    public void convertTextToPdf(final String textFileName) {
        final IPdfConverter converter = new BasicPdfConverter(textFileName, resourceTool);
        converter.convertTextToPdf();
    }

    public void convertPdfToDocx(final String pdfFileName) {
        final IPdfConverter converter = new BasicPdfConverter(pdfFileName, resourceTool);
        converter.convertPdfToDocx();
    }

    public void convertJsonToCsv(final String jsonFileName) {
        final var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(jsonFileName, FileFormat.JSON));
        final FromJsonConverter converter = new FromJsonConverter(resourceTool);
        try {
            converter.fromJsonTo(resource, FileFormat.CSV);
        } catch (IOException e) {
            throw new ServiceFailedException("Conversion error", e);
        }
    }

    public <T> void convertCsvToJson(final String csvFileName, final Class<T> jsonClass) {
        final FileFormat fileFormat = FileFormat.CSV;
        final var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(csvFileName, fileFormat));
        final ToJsonConverter<T> converter = new ToJsonConverter<>(resourceTool);
        try {
            converter.toJsonFrom(resource, fileFormat, jsonClass);
        } catch (IOException e) {
            throw new ServiceFailedException("Conversion error", e);
        }
    }

}