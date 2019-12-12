package com.fenixcommunity.centralspace.app.service.document;

import com.fenixcommunity.centralspace.app.service.document.converter.HtmlToPdfConverterStrategyType;
import com.fenixcommunity.centralspace.app.service.document.converter.HtmlToPdfConverterStrategy;
import com.fenixcommunity.centralspace.app.service.document.converter.ResourceConverterBean;
import com.fenixcommunity.centralspace.app.service.document.converter.ThymeleafResourceConverter;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.Map;

import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlToPdfConverterStrategyType.STANDARD;
import static com.fenixcommunity.centralspace.app.service.document.converter.HtmlToPdfConverterStrategyType.THYMELEAF;
import static java.util.Collections.singletonMap;

@Log4j2
@Service
public class DocumentService {
//todo to interface

    @Autowired
    private ResourceLoaderTool resourceTool;

    @Autowired
    private TemplateEngine templateEngine;

    public void convertHtmlToPdf(String htmlFileName, HtmlToPdfConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            Map<String, String> thymeleafVariables = singletonMap("imageUrl", resourceTool.getResourceProperties().getImageUrl());
            HtmlToPdfConverterStrategy converter = new ThymeleafResourceConverter(htmlFileName, thymeleafVariables,templateEngine, resourceTool);
            converter.convertHtmlToPdf();
        } else if (STANDARD == strategyType){
            HtmlToPdfConverterStrategy converter = new ResourceConverterBean(htmlFileName, resourceTool);
            converter.convertHtmlToPdf();
        }
    }

    public String getHtmlBody(String htmlFileName, HtmlToPdfConverterStrategyType strategyType) {
        if (THYMELEAF == strategyType) {
            Map<String, String> thymeleafVariables = singletonMap("imageUrl", resourceTool.getResourceProperties().getImageUrl());
            HtmlToPdfConverterStrategy converter = new ThymeleafResourceConverter(htmlFileName, thymeleafVariables,templateEngine, resourceTool);
           return converter.getHtmlBody();
        } else if (STANDARD == strategyType){
            HtmlToPdfConverterStrategy converter = new ResourceConverterBean(htmlFileName, resourceTool);
            return converter.getHtmlBody();
        }
        return "";
    }

}