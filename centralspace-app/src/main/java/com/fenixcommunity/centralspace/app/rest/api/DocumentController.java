package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.service.document.DocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fenixcommunity.centralspace.app.service.document.DocumentConverterStrategyType.THYMELEAF;

@RestController
@RequestMapping("/doc")
@Log4j2
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/html-to-pdf")
    public String htmlToPdf() {
        documentService.convertHtmlToPdf("html_to_pdf", THYMELEAF);
        return "DONE";
    }
}
