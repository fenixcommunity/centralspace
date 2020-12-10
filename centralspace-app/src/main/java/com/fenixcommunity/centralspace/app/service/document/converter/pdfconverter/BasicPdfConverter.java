package com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter;

import static com.fenixcommunity.centralspace.utilities.common.FileDevTool.createFileDirectories;
import static com.fenixcommunity.centralspace.utilities.common.FileDevTool.createNewOutputFile;
import static com.fenixcommunity.centralspace.utilities.common.FileFormat.*;
import static com.fenixcommunity.centralspace.utilities.common.Var.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

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
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.stream.Stream;

import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.app.globalexception.DocumentServiceException;
import com.fenixcommunity.centralspace.app.rest.caller.RestTemplateHelper;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.document.PdfDocumentComposer;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResourceLoader;
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
import lombok.experimental.FieldDefaults;
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
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class BasicPdfConverter implements IPdfConverter, HtmlPdfConverterStrategy {
//    testing -> PdfUnit library
    private static final int IMAGE_DPI = 500;
    private static final String READ_MODE = "r";

    private final String fileName;
    private final InternalResourceLoader resourceTool;

    @Override
    public void convertPdfToImage(final FileFormat fileFormat) {
        final var outputImagePath = resourceTool.getResourceProperties().getConvertedImagePath()
                + fileName + NUMBER_WATERMARK + DOT + fileFormat.getSubtype();
        try (var converterInput = PDDocument.load(getPDFFile())) {
            final PDFRenderer pdfRenderer = new PDFRenderer(converterInput);
            for (int page = 0; page < converterInput.getNumberOfPages(); ++page) {
                final var filePathWithPageNo = String.format(outputImagePath, page + 1);
                createFileDirectories(filePathWithPageNo);
                final BufferedImage image = pdfRenderer.renderImageWithDPI(
                        page, IMAGE_DPI, ImageType.RGB);
                ImageIOUtil.writeImage(
                        image, filePathWithPageNo, IMAGE_DPI);
            }
        } catch (IOException e) {
            log.error("convertPdfToImage error", e);
        }
    }

    @Override
    public void convertImageToPdf(final FileFormat fileFormat, final RestCallerStrategy restCallerStrategy) {
        final var inputImageUrl = resourceTool.getAbsoluteImagePath()
                + fileName + DOT + fileFormat.getSubtype();
        final var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + PDF.getSubtype();
        createFileDirectories(outputPdfPath);

        final var document = new Document();
        OutputStream converterOutput = null;
        PdfWriter writer = null;
        try {
            converterOutput = new FileOutputStream(outputPdfPath);
            writer = PdfWriter.getInstance(document, converterOutput);
            writer.open();
            document.open();

            final ResponseEntity<byte[]> responseRestTemplate = restCallerStrategy.getRestTemplate()
                    .exchange(inputImageUrl, HttpMethod.GET, RestTemplateHelper.createBasicHttpEntityWithHeaders(MediaType.ALL), byte[].class);
//            or another example
            final String responseWebClientUri = resourceTool.getAbsoluteImagePath() + "{file}";
            final Mono<byte[]> responseWebClient = restCallerStrategy.getWebClient()
                    .get()
                    .uri(responseWebClientUri, fileName + DOT + fileFormat.getSubtype()) // or builder if queryParam
//                  .body(BodyInserters.fromMultipartData(new LinkedMultiValueMap()) // add(k,v)
//                        BodyInserters.fromObject(new Long(2)) );
//                        Mono.just(...,)
//                    .bodyValue(javaObj)
                    .cookies(cookie -> cookie.add("cookieKey", "cookieValue"))
                    .headers(httpHeaders -> httpHeaders.setAccept(Collections.singletonList(MediaType.ALL)))
                    .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                    .acceptCharset(Charset.forName("UTF-8"))
                    .ifModifiedSince(ZonedDateTime.now())
                    .exchange() // retrieve when we need only body, exchange if headers etc
//                    .retrieve()  // -> for exchange()  @ExceptionHandler(WebClientResponseException.class)
//                    .onStatus(HttpStatus::is5xxServerError, clientResponse ->
//                            Mono.error(new DocumentServiceException())
//                    )
                    .flatMap(response -> response.bodyToMono(byte[].class)); // Mono -> single, Flux -> multiple


            final byte[] responseWebClientBody = responseWebClient.blockOptional()
                    .orElseThrow(() -> new DocumentServiceException("image not fetched"));
            responseWebClient.subscribe(response -> System.out.println(response.length));

            final Image image = Image.getInstance(requireNonNull(responseRestTemplate.getBody()));
            final Image image2 = Image.getInstance(requireNonNull(responseWebClientBody));
            PdfDocumentComposer.composeDocument(document);
            PdfDocumentComposer.composeImage(document, asList(image, image2));

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
        final var document = new Document();
        final var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + PDF.getSubtype();
        try (var converterInput = new FileInputStream(getHtmlFile());
             var converterOutput = new FileOutputStream(requireNonNull(createNewOutputFile(outputPdfPath)), false)) {
            final var writer = PdfWriter.getInstance(document, converterOutput);
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
            final var htmlFile = Jsoup.parse(getHtmlFile(), UTF_8.name());
            return htmlFile.toString();
        } catch (IOException e) {
            log.error("getHtmlBody error", e);
        }
        return "";
    }

    private File getHtmlFile() throws IOException {
        final var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, HTML));
        return resource.getFile();
    }

    @Override
    public void convertPdfToHtml() {
        final var outputHtmlPath = resourceTool.getResourceProperties().getConvertedHtmlPath()
                + fileName + DOT + HTML.getSubtype();
        try (var converterInput = PDDocument.load(getPDFFile());
             var converterOutput = new PrintWriter(requireNonNull(createNewOutputFile(outputHtmlPath)))) {
            new PDFDomTree().writeText(converterInput, converterOutput);
        } catch (IOException | ParserConfigurationException e) {
            log.error("convertPdfToHtml error", e);
            //todo throw exception
        }
    }

    //todo special class to get pdf or html file
    private File getPDFFile() throws IOException {
        final var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(fileName, PDF));
        return resource.getFile();
    }

    @Override
    public void convertPdfToText() {
        String parsedText = EMPTY;
        PDDocument document = null;
        var outputTxtPath = resourceTool.getResourceProperties().getConvertedTxtPath()
                + fileName + DOT + TXT.getSubtype();
        try (var converterOutput = new PrintWriter(requireNonNull(createNewOutputFile(outputTxtPath)))) {
            final PDFParser pdfParser = new PDFParser(new RandomAccessFile(getPDFFile(), READ_MODE));
            pdfParser.parse();
            final COSDocument cosDoc = pdfParser.getDocument();
            final PDFTextStripper pdfStripper = new PDFTextStripper();
            document = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(document);
            converterOutput.print(parsedText);
        } catch (IOException e) {
            log.error("convertPdfToText error", e);
            throw new DocumentServiceException(e.getMessage(), e);
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
            } catch (IOException e) {
                //ignore
            }
        }
    }

    @Override
    public void convertTextToPdf() {
        //todo final?
        final var document = new Document(PageSize.A4);

        final var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + PDF.getSubtype();
        try (var converterInput = new BufferedReader(new FileReader(getTxtFile()));
             var converterOutput = new FileOutputStream(requireNonNull(createNewOutputFile(outputPdfPath)))) {
            PdfWriter.getInstance(document, converterOutput).setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            document.open();
            document.add(new Paragraph("\n"));

            final var font = PdfDocumentComposer.composeFont();

            try (Stream<String> stream = converterInput.lines()) {
                stream.forEach(line -> {
                    final Paragraph paragraph = new Paragraph(line + "\n", font);
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
        final var resource = resourceTool.loadResourceFile(InternalResource.resourceByFullName(fileName + DOT + TXT.getSubtype()));
        return resource.getFile();
    }

    @Override
    public void convertPdfToDocx() {
        final XWPFDocument document = new XWPFDocument();
        PdfReader reader = null;

        final var outputDocxPath = resourceTool.getResourceProperties().getConvertedDocxPath()
                + fileName + DOT + DOCX.getSubtype();
        try (var converterInput = new FileInputStream(getPDFFile());
             var converterOutput = new FileOutputStream(requireNonNull(createNewOutputFile(outputDocxPath)))) {
            reader = new PdfReader(converterInput);
            final PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                var textExtractionStrategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                final String text = textExtractionStrategy.getResultantText();
                final XWPFParagraph p = document.createParagraph();
                final XWPFRun run = p.createRun();
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
                //ignore
            }
        }
    }
}
