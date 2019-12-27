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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.http.MediaType;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.createNewOutputFile;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
@AllArgsConstructor
public class BasicPdfConverter implements IPdfConverter, HtmlPdfConverterStrategy {

    private String fileName;
    private final ResourceLoaderTool resourceTool;

    @Override
    public void convertHtmlToPdf() {
        var document = new Document();
        var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath() + SLASH + fileName + DOT + MediaType.APPLICATION_PDF.getSubtype();
        try (var converterInput = new FileInputStream(getHtmlFile());
             var converterOutput = new FileOutputStream(Objects.requireNonNull(createNewOutputFile(outputPdfPath)), false)) {
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
            var htmlFile = Jsoup.parse(getHtmlFile(), UTF_8.name());
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

    @Override
    public void convertPdfToHtml() {
        var outputHtmlPath = resourceTool.getResourceProperties().getConvertedHtmlPath() + SLASH + fileName + DOT + MediaType.TEXT_HTML.getSubtype();
        try (var converterInput = PDDocument.load(getPDFFile());
             var converterOutput = new PrintWriter(Objects.requireNonNull(createNewOutputFile(outputHtmlPath)))) {
            new PDFDomTree().writeText(converterInput, converterOutput);
        } catch (IOException | ParserConfigurationException e) {
            log.error("convertPdfToHtml error", e);
            //todo throw exception
        }
    }

    //todo special class to get pdf or html file
    private File getPDFFile() throws IOException {
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, MediaType.APPLICATION_PDF));
        return resource.getFile();
    }

    @Override
    public void convertPdfToText() {
    }

    @Override
    public void convertTextToPdf() {

    }

    @Override
    public void convertPdfToDocx() {

    }

    @Override
    public void convertPdfToImage(MediaType extension) {
        String pageWatermark = "%d";
        var outputImagePath = resourceTool.getResourceProperties().getConvertedImagePath() + SLASH + fileName + pageWatermark + DOT + extension.getSubtype();
        try (var converterInput = PDDocument.load(getPDFFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(converterInput);
            for (int page = 0; page < converterInput.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(
                        page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(
                        bim, String.format(outputImagePath, page + 1), 500);
            }
        } catch (IOException e) {
            log.error("convertPdfToImage error", e);
        }
    }

    @Override
    public void convertImageToPdf() {

    }
}
