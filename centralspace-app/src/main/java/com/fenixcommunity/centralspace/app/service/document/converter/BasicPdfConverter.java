package com.fenixcommunity.centralspace.app.service.document.converter;

import com.fenixcommunity.centralspace.app.exception.DocumentServiceException;
import com.fenixcommunity.centralspace.app.rest.caller.RestTemplateHelper;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.createFileDirectories;
import static com.fenixcommunity.centralspace.utilities.common.DevTool.createNewOutputFile;
import static com.fenixcommunity.centralspace.utilities.common.FileFormat.*;
import static com.fenixcommunity.centralspace.utilities.common.Var.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
@AllArgsConstructor
public class BasicPdfConverter implements IPdfConverter, HtmlPdfConverterStrategy {

    private static final int IMAGE_DPI = 500;
    public static final String READ_MODE = "r";

    private String fileName;
    private final ResourceLoaderTool resourceTool;

    @Override
    public void convertPdfToImage(FileFormat fileFormat) {
        var outputImagePath = resourceTool.getResourceProperties().getConvertedImagePath()
                + fileName + NUMBER_WATERMARK + DOT + fileFormat.getSubtype();
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
    public void convertImageToPdf(FileFormat fileFormat, RestTemplate restTemplate) {
        var inputImageUrl = resourceTool.getResourceProperties().getImageUrl()
                + fileName + DOT + fileFormat.getSubtype();
        var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + PDF.getSubtype();
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
                + fileName + DOT + PDF.getSubtype();
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
            //yes, should be like in convertImageToPdf. But I'm so lazy
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
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, HTML));
        return resource.getFile();
    }

    @Override
    public void convertPdfToHtml() {
        var outputHtmlPath = resourceTool.getResourceProperties().getConvertedHtmlPath()
                + fileName + DOT + HTML.getSubtype();
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
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, PDF));
        return resource.getFile();
    }

    @Override
    public void convertPdfToText() {
        String parsedText = EMPTY;
        PDDocument document = new PDDocument();
        var outputTxtPath = resourceTool.getResourceProperties().getConvertedTxtPath()
                + fileName + DOT + TXT.getSubtype();
        try (var converterOutput = new PrintWriter(Objects.requireNonNull(createNewOutputFile(outputTxtPath)))) {
            PDFParser pdfParser = new PDFParser(new RandomAccessFile(getPDFFile(), READ_MODE));
            pdfParser.parse();
            COSDocument cosDoc = pdfParser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            document = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(document);
            converterOutput.print(parsedText);
        } catch (IOException e) {
            log.error("convertPdfToText error", e);
            throw new DocumentServiceException(e.getMessage(), e);
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                throw new DocumentServiceException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void convertTextToPdf() {
        //todo final?
        final var document = new Document(PageSize.A4);

        var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + PDF.getSubtype();
        try (var converterInput = new BufferedReader(new FileReader(getTxtFile()));
             var converterOutput = new FileOutputStream(Objects.requireNonNull(createNewOutputFile(outputPdfPath)))) {
            PdfWriter.getInstance(document, converterOutput).setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            document.open();
            document.add(new Paragraph("\n"));

            var font = DocumentComposer.composeFont();

            try (Stream<String> stream = converterInput.lines()) {
                stream.forEach(line -> {
                    Paragraph paragraph = new Paragraph(line + "\n", font);
                    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                    try {
                        document.add(paragraph);
                    } catch (DocumentException e) {
                        throw new DocumentServiceException(e.getMessage(), e);
                    }
                });
                document.close();
            }
        } catch (IOException | DocumentException e) {
            log.error("convertTextToPdf error", e);
            throw new DocumentServiceException(e.getMessage(), e);
        } finally {
            //yes, should be like in convertImageToPdf. But I'm so lazy
        }
    }

    private File getTxtFile() throws IOException {
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByFullName(fileName + DOT + TXT.getSubtype()));
        return resource.getFile();
    }

    @Override
    public void convertPdfToDocx() {
        XWPFDocument document = new XWPFDocument();
        PdfReader reader = null;

        var outputDocxPath = resourceTool.getResourceProperties().getConvertedDocxPath()
                + fileName + DOT + DOCX.getSubtype();
        try (var converterInput = new FileInputStream(getPDFFile());
             var converterOutput = new FileOutputStream(Objects.requireNonNull(createNewOutputFile(outputDocxPath)))) {
            reader = new PdfReader(converterInput);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                var textExtractionStrategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                String text = textExtractionStrategy.getResultantText();
                XWPFParagraph p = document.createParagraph();
                XWPFRun run = p.createRun();
                run.setText(text);
                run.addBreak(BreakType.PAGE);

            }
            document.write(converterOutput);
        } catch (IOException e) {
            log.error("convertTextToPdf error", e);
            throw new DocumentServiceException(e.getMessage(), e);
        } finally {
            if (reader != null) reader.close();
            try {
                document.close();
            } catch (IOException e) {
                throw new DocumentServiceException(e.getMessage(), e);
            }
        }
    }
}
