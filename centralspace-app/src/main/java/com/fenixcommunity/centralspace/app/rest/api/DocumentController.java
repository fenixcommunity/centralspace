package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.service.document.DocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategyType.BASIC;
import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlPdfConverterStrategyType.THYMELEAF;
import static com.fenixcommunity.centralspace.utilities.common.Var.IMAGE;
import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;

@RestController
@RequestMapping("/doc")
@Log4j2
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/pdf-to-image")
    public void convertPdfToImage(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_image") String pdfFileName,
                                  @RequestParam(value = "extension", defaultValue = "png") String extension) {
        documentService.convertPdfToImage(pdfFileName, MediaType.parseMediaType(IMAGE + SLASH + extension));
    }

    @GetMapping("/image-to-pdf-as-admin")
    public void convertImageToPdfAsAdmin(@RequestParam(value = "imageFile", defaultValue = "image_to_pdf") String imageFileName,
                                         @RequestParam(value = "extension", defaultValue = "png") String extension) {
        documentService.convertImageToPdfAsAdmin(imageFileName, MediaType.parseMediaType(IMAGE + SLASH + extension));
    }

    @GetMapping("/html-to-pdf")
    public String htmlToPdf(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") String htmlFileName) {
        documentService.convertHtmlToPdf(htmlFileName, BASIC);
        return "STANDARD DONE";
    }

    @GetMapping("/html-to-pdf-thymeleaf")
    public String htmlToPdfByThymeleaf(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") String htmlFileName) {
        documentService.convertHtmlToPdf(htmlFileName, THYMELEAF);
        return "THYMELEAF DONE";
    }

    @GetMapping("/pdf-to-html")
    public String pdfToHtml(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_html") String pdfFileName) {
        documentService.convertPdfToHtml(pdfFileName, BASIC);
        return "STANDARD DONE";
    }

    @GetMapping("/pdf-to-html-thymeleaf")
    public String pdfToHtmlByThymeleaf(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_html") String pdfFileName) {
        documentService.convertPdfToHtml(pdfFileName, THYMELEAF);
        return "THYMELEAF DONE";
    }

    @GetMapping("/html-body")
    public String getHtmlBody(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") String htmlFileName) {
        String htmlBody = documentService.getHtmlBody(htmlFileName, BASIC);
        return htmlBody;
    }

    @GetMapping("/html-body-thymeleaf")
    public String getHtmlBodyByThymeleaf(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") String htmlFileName) {
        String htmlBody = documentService.getHtmlBody(htmlFileName, THYMELEAF);
        return htmlBody;
    }

    @GetMapping("/pdf-to-text")
    public void convertPdfToText(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_text") String pdfFileName) {
        documentService.convertPdfToText(pdfFileName);
    }

    @GetMapping("/text-to-pdf")
    public void convertTextToPdf(@RequestParam(value = "textFile", defaultValue = "text_to_pdf") String textFileName) {
        documentService.convertTextToPdf(textFileName);
    }

    @GetMapping("/pdf-to-docx")
    public void convertPdfToDocx(@RequestParam(value = "pdfFile", defaultValue = "pdf_to_docx") String pdfFileName) {
        documentService.convertPdfToDocx(pdfFileName);
    }
}
