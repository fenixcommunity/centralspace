package com.fenixcommunity.centralspace.app.configuration;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOMAIN_URL;
import static com.fenixcommunity.centralspace.utilities.common.Var.EMAIL;
import static com.google.common.collect.Lists.newArrayList;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${springfox.swagger2.host}")
    private String swagger2Host;

    @Bean
    public Docket getApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(swagger2Host)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fenixcommunity.centralspace.app.rest.api"))
                .paths(pathsFilter())
                .build()
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, errorList())
                .globalResponseMessage(RequestMethod.POST, errorList())
                .globalResponseMessage(RequestMethod.PUT, errorList())
                .globalResponseMessage(RequestMethod.DELETE, errorList());
    }

    private Predicate<String> pathsFilter() {
        return input -> input.matches("/account/.*") ||
                input.matches("/doc/.*") ||
                input.matches("/mail/.*") ||
                input.matches("/password/.*") ||
                input.matches("/register/.*") ||
                input.matches("/resource/.*");
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Fenix community")
                .description("Centralspace app")
                .version("Version 1.0")
                .contact(new Contact("MK", DOMAIN_URL, EMAIL))
                .build();
    }

    private List<ResponseMessage> errorList() {
        List<ResponseMessage> errorList = new ArrayList<>();

        errorList.add(new ResponseMessageBuilder().code(400).message("Bad Request")
                .responseModel(new ModelRef("ErrorInfo")).build());
        errorList.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
                .responseModel(new ModelRef("ErrorInfo")).build());
        errorList.add(new ResponseMessageBuilder().code(402).message("Forbidden")
                .responseModel(new ModelRef("ErrorInfo")).build());
        errorList.add(new ResponseMessageBuilder().code(404).message("Not Found")
                .responseModel(new ModelRef("ErrorInfo")).build());
//todo newArrayList(
        errorList.add(new ResponseMessageBuilder().code(500).message("Internal Server Error")
                .responseModel(new ModelRef("ErrorInfo")).build());

//todo or, what about ErrorInfo?
//        @ApiResponses(value = {
//                @ApiResponse(code = 200, response = BasicResponse.class, message = "OK"),
//                @ApiResponse(code = 503, response = ServiceFailedException.class, message = "xxx"),
//                @ApiResponse(code = 400, message = "Bad request"),
//                @ApiResponse(code = 404, message = "Not found"),
//                @ApiResponse(code = 501, message = "Not implemented for given extraction type")
//        })

        return errorList;
    }

}