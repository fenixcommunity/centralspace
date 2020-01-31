package com.fenixcommunity.centralspace.app.service.document.converter;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.createNewOutputFile;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Log4j2
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class ThymeleafPdfConverter implements HtmlPdfConverterStrategy {

    private final String fileName;
    private final Map<String, String> thymeleafVariables;

    private final TemplateEngine templateEngine;
    private final ResourceLoaderTool resourceTool;

    @Override
    public void convertHtmlToPdf() {
        final var htmlContent = getHtmlBody();
        final var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + FileFormat.PDF.getSubtype();
        try (var fileStream = new FileOutputStream(Objects.requireNonNull(createNewOutputFile(outputPdfPath)), false)) {
            HtmlConverter.convertToPdf(htmlContent, fileStream);
        } catch (IOException e) {
            log.error("convertHtmlToPdf error", e);
        }
    }

    @Override
    public void convertPdfToHtml() {
        final var converter = new BasicPdfConverter(fileName, resourceTool);
        converter.convertPdfToHtml();
    }

    @Override
    public String getHtmlBody() {
        final var templateContext = new Context();
        thymeleafVariables.entrySet().forEach(x ->
                templateContext.setVariable(x.getKey(), x.getValue()));
        return templateEngine.process(fileName, templateContext);
    }
}
