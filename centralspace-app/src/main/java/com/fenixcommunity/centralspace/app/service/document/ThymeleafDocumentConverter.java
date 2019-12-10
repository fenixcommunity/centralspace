package com.fenixcommunity.centralspace.app.service.document;

import com.itextpdf.html2pdf.HtmlConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.createNewOutputFile;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;

@Log4j2
public class ThymeleafDocumentConverter implements DocumentConverterStrategy {

    private final TemplateEngine templateEngine;
    private String fileName;
    private Map<String,String> thymeleafVariables;

    private static final String services_properties = "services.properties";
    private static final String CONVERTED_PDF_PATH = "convertedPdf.path";
    private Properties properties;


    public ThymeleafDocumentConverter(String fileName, Map<String, String> thymeleafVariables, TemplateEngine templateEngine) {
        this.fileName = fileName;
        this.thymeleafVariables = thymeleafVariables;
        this.templateEngine = templateEngine;
        initProperties();

    }

    private void initProperties() {
        properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(ThymeleafDocumentConverter.class.getClassLoader().getResourceAsStream(services_properties)));
        } catch (IOException e) {
            log.error( "Unsuccessful loading the properties to ThymeleafDocumentConverter", e);
        }
    }

    @Override
    public void convertHtmlToPdf() {
        String htmlContent = getHtmlBody();
        try {
            String convertedPdfPath = properties.getProperty(CONVERTED_PDF_PATH) + SLASH + fileName + DOT + MediaType.APPLICATION_PDF.getSubtype();
            OutputStream fileStream = new FileOutputStream(Objects.requireNonNull(createNewOutputFile(convertedPdfPath)), false);
            HtmlConverter.convertToPdf(htmlContent, fileStream);
            fileStream.close();
        }  catch (IOException e) {
            log.error("ConvertToPdf error", e);
        }
    }

    @Override
    public String getHtmlBody() {
        Context templateContext = new Context();
        thymeleafVariables.entrySet().forEach(x->
                templateContext.setVariable(x.getKey(), x.getValue()));
        return templateEngine.process(fileName, templateContext);
    }
}
