package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.service.document.DocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlToPdfConverterStrategyType.STANDARD;
import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlToPdfConverterStrategyType.THYMELEAF;

@RestController
@RequestMapping("/doc")
@Log4j2
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/html-to-pdf")
    public String htmlToPdf(@RequestParam(value = "htmlFile") String htmlFileName) {
        documentService.convertHtmlToPdf(htmlFileName, STANDARD);
        return "STANDARD DONE";
    }

    @GetMapping("/html-to-pdf-thymeleaf")
    public String htmlToPdfByThymeleaf(@RequestParam(value = "htmlFile") String htmlFileName) {
        documentService.convertHtmlToPdf(htmlFileName, THYMELEAF);
        return "THYMELEAF DONE";
    }

    @GetMapping("/html-body")
    public String getHtmlBody(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") String htmlFileName) {
        String htmlBody = documentService.getHtmlBody(htmlFileName, STANDARD);
        return htmlBody;
    }

    @GetMapping("/html-body-thymeleaf")
    public String getHtmlBodyByThymeleaf(@RequestParam(value = "htmlFile", defaultValue = "html_to_pdf") String htmlFileName) {
        String htmlBody = documentService.getHtmlBody(htmlFileName, THYMELEAF);
        return htmlBody;
    }

}
