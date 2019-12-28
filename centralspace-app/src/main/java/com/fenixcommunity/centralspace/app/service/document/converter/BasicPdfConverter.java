package com.fenixcommunity.centralspace.app.service.document.converter;

import com.fenixcommunity.centralspace.app.rest.caller.RestTemplateHelper;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Objects;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.createFileDirectories;
import static com.fenixcommunity.centralspace.utilities.common.DevTool.createNewOutputFile;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.NUMBER_WATERMARK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.http.MediaType.TEXT_HTML;

@Log4j2
@AllArgsConstructor
public class BasicPdfConverter implements IPdfConverter, HtmlPdfConverterStrategy {

    private static final int IMAGE_DPI = 500;
    private String fileName;
    private final ResourceLoaderTool resourceTool;

    @Override
    public void convertPdfToImage(MediaType extension) {
        var outputImagePath = resourceTool.getResourceProperties().getConvertedImagePath()
                + fileName + NUMBER_WATERMARK + DOT + extension.getSubtype();
        try (var converterInput = PDDocument.load(getPDFFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(converterInput);
            for (int page = 0; page < converterInput.getNumberOfPages(); ++page) {
                var filePathWithPageNo = String.format(outputImagePath, page + 1);
                createFileDirectories(filePathWithPageNo);
                BufferedImage image = pdfRenderer.renderImageWithDPI(
                        page, IMAGE_DPI, ImageType.RGB);
                ImageIOUtil.writeImage(
                        image, filePathWithPageNo, IMAGE_DPI);
            }
        } catch (IOException e) {
            log.error("convertPdfToImage error", e);
        }
    }

    @Override
    public void convertImageToPdf(MediaType extension, RestTemplate restTemplate) {
        var inputImageUrl = resourceTool.getResourceProperties().getImageUrl()
                + fileName + DOT + extension.getSubtype();
        var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + APPLICATION_PDF.getSubtype();
        createFileDirectories(outputPdfPath);

        var document = new Document();
        OutputStream converterOutput = null;
        PdfWriter writer = null;
        try {
            converterOutput = new FileOutputStream(outputPdfPath);
            writer = PdfWriter.getInstance(document, converterOutput);
            writer.open();
            document.open();

            ResponseEntity<byte[]> response = restTemplate
                    .exchange(inputImageUrl, HttpMethod.GET, RestTemplateHelper.createHttpEntityWithHeaders(MediaType.ALL), byte[].class);
//            or
//            ResponseEntity<byte[]> response2 =restTemplate.getForEntity(inputImageUrl, byte[].class);
            Image image = Image.getInstance(Objects.requireNonNull(response.getBody()));
            document = DocumentComposer.composeDocument(document);
            document = DocumentComposer.composeImage(document, Collections.singletonList(image));

            writer.flush();
        } catch (Exception e) {
            log.error("convertImageToPdf error", e);
        } finally {
            document.close();
            if (writer != null) writer.close();
            if (converterOutput != null) {
                try {
                    converterOutput.close();
                } catch (IOException e) {
                    log.error("convertImageToPdf close stream error", e);
                }
            }
        }
    }

    @Override
    public void convertHtmlToPdf() {
        var document = new Document();
        var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + APPLICATION_PDF.getSubtype();
        try (var converterInput = new FileInputStream(getHtmlFile());
             var converterOutput = new FileOutputStream(Objects.requireNonNull(createNewOutputFile(outputPdfPath)), false)) {
            var writer = PdfWriter.getInstance(document, converterOutput);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, converterInput);
            document.close();
            writer.close();
        } catch (IOException | DocumentException e) {
            log.error("convertHtmlToPdf error", e);
        } finally {
            //todo should be like in convertImageToPdf
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
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, TEXT_HTML));
        return resource.getFile();
    }

    @Override
    public void convertPdfToHtml() {
        var outputHtmlPath = resourceTool.getResourceProperties().getConvertedHtmlPath()
                + fileName + DOT + TEXT_HTML.getSubtype();
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
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, APPLICATION_PDF));
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
}
