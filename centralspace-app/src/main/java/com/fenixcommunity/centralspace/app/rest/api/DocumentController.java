package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategyType.BASIC;
import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategyType.THYMELEAF;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.service.document.DocumentService;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/doc")
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/create-pdf")
    public void createPdf(@RequestParam(value = "pdfFile", defaultValue = "created_pdf") final String pdfFileName) {
        documentService.createPdf(pdfFileName);
    }

    @GetMapping("/pdf-to-image")
    public void convertPdfToImage(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_image") final String pdfFileName,
                                  @RequestParam(value = "fileFormat", defaultValue = "png") final String fileFormat) {
        documentService.convertPdfToImage(pdfFileName, FileFormat.parseFileFormat(fileFormat));
    }

    @GetMapping("/image-to-pdf-as-admin-by-web-client-and-rest-template")
    public void convertImageToPdfAsAdminByWebClient(@RequestParam(value = "imageFile", defaultValue = "image_to_pdf") final String imageFileName,
                                                    @RequestParam(value = "fileFormat", defaultValue = "png") final String fileFormat) {
        documentService.convertImageToPdfAsAdminByWebClientAndRestTemplate(imageFileName, FileFormat.parseFileFormat(fileFormat));
    }

    @GetMapping("/html-to-pdf")
    public String htmlToPdf(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") final String htmlFileName) {
        documentService.convertHtmlToPdf(htmlFileName, BASIC);
        return "STANDARD DONE";
    }

    @GetMapping("/html-to-pdf-thymeleaf")
    public String htmlToPdfByThymeleaf(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") final String htmlFileName) {
        documentService.convertHtmlToPdf(htmlFileName, THYMELEAF);
        return "THYMELEAF DONE";
    }

    @GetMapping("/pdf-to-html")
    public String pdfToHtml(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_html") final String pdfFileName) {
        documentService.convertPdfToHtml(pdfFileName, BASIC);
        return "STANDARD DONE";
    }

    @GetMapping("/pdf-to-html-thymeleaf")
    public String pdfToHtmlByThymeleaf(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_html") final String pdfFileName) {
        documentService.convertPdfToHtml(pdfFileName, THYMELEAF);
        return "THYMELEAF DONE";
    }

    @GetMapping("/html-body")
    public String getHtmlBody(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") final String htmlFileName) {
        final String htmlBody = documentService.getHtmlBody(htmlFileName, BASIC);
        return htmlBody;
    }

    @GetMapping("/html-body-thymeleaf")
    public String getHtmlBodyByThymeleaf(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") final String htmlFileName) {
        final String htmlBody = documentService.getHtmlBody(htmlFileName, THYMELEAF);
        return htmlBody;
    }

    @GetMapping("/pdf-to-txt")
    public void convertPdfToText(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_txt") final String pdfFileName) {
        documentService.convertPdfToText(pdfFileName);
    }

    @GetMapping("/txt-to-pdf")
    public void convertTextToPdf(@RequestParam(value = "textFile", defaultValue = "txt_to_pdf") final String textFileName) {
        documentService.convertTextToPdf(textFileName);
    }

    @GetMapping("/pdf-to-docx")
    public void convertPdfToDocx(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_docx") final String pdfFileName) {
        documentService.convertPdfToDocx(pdfFileName);
    }
}
