package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.HtmlPdfConverterStrategyType.BASIC;
import static com.fenixcommunity.centralspace.app.service.document.converter.pdfconverter.HtmlPdfConverterStrategyType.THYMELEAF;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.fenixcommunity.centralspace.app.rest.dto.converter.JsonConverterResultDto;
import com.fenixcommunity.centralspace.app.service.document.DocumentService;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/doc")
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true) @AllArgsConstructor(access = PACKAGE)
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/create-pdf")
    public ResponseEntity createPdf(@RequestParam(value = "pdfFileName", defaultValue = "created_pdf") final String pdfFileName) {
        documentService.createPdf(pdfFileName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pdf-to-image")
    public ResponseEntity convertPdfToImage(@RequestParam(value = "pdfFileName", defaultValue = "pdf_to_image") final String pdfFileName,
                                  @RequestParam(value = "fileFormat", defaultValue = "png") final String fileFormat) {
        documentService.convertPdfToImage(pdfFileName, FileFormat.parseFileFormat(fileFormat));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image-to-pdf-as-admin-by-web-client-and-rest-template")
    public ResponseEntity convertImageToPdfAsAdminByWebClient(@RequestParam(value = "imageFileName", defaultValue = "image_to_pdf") final String imageFileName,
                                                    @RequestParam(value = "fileFormat", defaultValue = "png") final String fileFormat) {
        documentService.convertImageToPdfAsAdminByWebClientAndRestTemplate(imageFileName, FileFormat.parseFileFormat(fileFormat));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/html-to-pdf")
    public ResponseEntity<String> htmlToPdf(@RequestParam(value = "htmlFileName", defaultValue = "html_to_pdf") final String htmlFileName) {
        documentService.convertHtmlToPdf(htmlFileName, BASIC);
        return ResponseEntity.ok("STANDARD DONE");
    }

    @GetMapping("/html-to-pdf-thymeleaf")
    public ResponseEntity<String> htmlToPdfByThymeleaf(@RequestParam(value = "htmlFileName", defaultValue = "html_to_pdf") final String htmlFileName) {
        documentService.convertHtmlToPdf(htmlFileName, THYMELEAF);
        return ResponseEntity.ok("THYMELEAF DONE");
    }

    @GetMapping("/pdf-to-html")
    public ResponseEntity<String> pdfToHtml(@RequestParam(value = "pdfFileName", defaultValue = "pdf_to_html") final String pdfFileName) {
        documentService.convertPdfToHtml(pdfFileName, BASIC);
        return ResponseEntity.ok("STANDARD DONE");
    }

    @GetMapping("/pdf-to-html-thymeleaf")
    public ResponseEntity<String> pdfToHtmlByThymeleaf(@RequestParam(value = "pdfFileName", defaultValue = "pdf_to_html") final String pdfFileName) {
        documentService.convertPdfToHtml(pdfFileName, THYMELEAF);
        return ResponseEntity.ok("THYMELEAF DONE");
    }

    @GetMapping("/html-body")
    public String getHtmlBody(@RequestParam(value = "htmlFileName", defaultValue = "html_to_pdf") final String htmlFileName) {
        final String htmlBody = documentService.getHtmlBody(htmlFileName, BASIC);
        return htmlBody;
    }

    @GetMapping("/html-body-thymeleaf")
    public String getHtmlBodyByThymeleaf(@RequestParam(value = "htmlFileName", defaultValue = "html_to_pdf") final String htmlFileName) {
        final String htmlBody = documentService.getHtmlBody(htmlFileName, THYMELEAF);
        return htmlBody;
    }

    @GetMapping("/pdf-to-txt")
    public ResponseEntity convertPdfToText(@RequestParam(value = "pdfFileName", defaultValue = "pdf_to_txt") final String pdfFileName) {
        documentService.convertPdfToText(pdfFileName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/txt-to-pdf")
    public ResponseEntity convertTextToPdf(@RequestParam(value = "textFileName", defaultValue = "txt_to_pdf") final String textFileName) {
        documentService.convertTextToPdf(textFileName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pdf-to-docx")
    public ResponseEntity convertPdfToDocx(@RequestParam(value = "pdfFileName", defaultValue = "pdf_to_docx") final String pdfFileName) {
        documentService.convertPdfToDocx(pdfFileName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/json-to-csv")
    public ResponseEntity convertJsonToCsv(@RequestParam(value = "jsonFileName", defaultValue = "json_to_csv") final String jsonFileName) {
        documentService.convertJsonToCsv(jsonFileName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/csv-to-json")
    public ResponseEntity convertCsvToJson(@RequestParam(value = "csvFileName", defaultValue = "csv_to_json") final String csvFileName) {
        documentService.convertCsvToJson(csvFileName, JsonConverterResultDto.class);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/csv-to-list")
    public ResponseEntity<String> convertCsvToList(@RequestParam(value = "csvFileName", defaultValue = "csv_to_json") final String csvFileName) {
        final List<List<String>> results = documentService.convertCsvToList(csvFileName);
        return ResponseEntity.ok(results.toString());
    }

}
