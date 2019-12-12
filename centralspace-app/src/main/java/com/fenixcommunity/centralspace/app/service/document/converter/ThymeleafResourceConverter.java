package com.fenixcommunity.centralspace.app.service.document.converter;

import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.createNewOutputFile;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;

@Log4j2
@AllArgsConstructor
public class ThymeleafResourceConverter implements HtmlToPdfConverterStrategy {

    private String fileName;
    private Map<String,String> thymeleafVariables;

    private final TemplateEngine templateEngine;
    private final ResourceLoaderTool resourceTool;

    @Override
    public void convertHtmlToPdf() {
        var htmlContent = getHtmlBody();
        var convertedPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath() + SLASH + fileName + DOT + MediaType.APPLICATION_PDF.getSubtype();
        try(var fileStream = new FileOutputStream(Objects.requireNonNull(createNewOutputFile(convertedPdfPath)), false)) {
            HtmlConverter.convertToPdf(htmlContent, fileStream);
        }  catch (IOException e) {
            log.error("convertHtmlToPdf error", e);
        }
    }

    @Override
    public String getHtmlBody() {
        var templateContext = new Context();
        thymeleafVariables.entrySet().forEach(x->
                templateContext.setVariable(x.getKey(), x.getValue()));
        return templateEngine.process(fileName, templateContext);
    }
}
