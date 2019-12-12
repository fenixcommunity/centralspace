package com.fenixcommunity.centralspace.app.service.document.converter;

import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.createNewOutputFile;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;

@Log4j2
@AllArgsConstructor
public class ResourceConverterBean implements HtmlToPdfConverterStrategy {

    private String fileName;
    private final ResourceLoaderTool resourceTool;

    @Override
    public void convertHtmlToPdf() {
        var document = new Document();
        var convertedPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath() + SLASH + fileName + DOT + MediaType.APPLICATION_PDF.getSubtype();
        try (var converterInput = new FileInputStream(getHtmlFile());
             var converterOutput = new FileOutputStream(Objects.requireNonNull(createNewOutputFile(convertedPdfPath)), false)) {
            var writer = PdfWriter.getInstance(document, converterOutput);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, converterInput);
            document.close();
        } catch (IOException | DocumentException e) {
            log.error("convertHtmlToPdf error", e);
        }
    }

    //todo var all app
    @Override
    public String getHtmlBody() {
        try {
            var htmlFile = Jsoup.parse(getHtmlFile(), StandardCharsets.UTF_8.name());
            return htmlFile.toString();
        } catch (IOException e) {
            log.error("getHtmlBody error", e);
        }
        return "";
    }

    private File getHtmlFile() throws IOException {
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, MediaType.TEXT_HTML));
        return resource.getFile();
    }
}
