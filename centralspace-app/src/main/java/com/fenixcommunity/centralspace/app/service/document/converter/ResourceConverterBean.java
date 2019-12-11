package com.fenixcommunity.centralspace.app.service.document.converter;

import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;

@Log4j2
@AllArgsConstructor
public class ResourceConverterBean implements HtmlToPdfConverterStrategy {

    private String fileName;
    private final ResourceLoaderTool resourceTool;

    @Override
    public void convertHtmlToPdf() {
        Resource resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, MediaType.TEXT_HTML));
        try {
            PDDocument pdf = PDDocument.load(resource.getFile());
            String convertedPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath() + SLASH + fileName + DOT + MediaType.APPLICATION_PDF.getSubtype();
            Writer output = new PrintWriter(convertedPdfPath, StandardCharsets.UTF_8);
            new PDFDomTree().writeText(pdf, output);
            output.close();
            //todo finally
        }  catch (IOException | ParserConfigurationException e) {
            log.error("convertHtmlToPdf error", e);
        }
    }

    @Override
    public String getHtmlBody() {
        Resource resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, MediaType.TEXT_HTML));
        try {
            Document htmlFile = Jsoup.parse(resource.getFile(), StandardCharsets.UTF_8.name());
            return htmlFile.toString();
        } catch (IOException e) {
            log.error("getHtmlBody error", e);
        }
        return "";
    }
}
