package com.fenixcommunity.centralspace.app.configuration.swaggerdoc;

import com.fenixcommunity.centralspace.app.rest.exception.ErrorDetails;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.getSimpleClassName;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOMAIN_URL;
import static com.fenixcommunity.centralspace.utilities.common.Var.EMAIL;
import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@EnableSwagger2WebMvc
@Import({BeanValidatorPluginsConfiguration.class}) //SpringDataRestConfiguration.class no works for latest Spring
@Configuration
//@Profile(Profiles.SWAGGER_ENABLED_PROFILE)
public class SwaggerConfig implements WebMvcConfigurer {
//todo block try api for readonly
    private static final String REST_PACKAGE = "com.fenixcommunity.centralspace.app.rest";

    @Value("${app.path}")
    private String APP_PATH;

    @Value("${springfox.swagger2.host}")
    private String swagger2Host;

    @Bean
    public Docket getDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .host(swagger2Host)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, errorList())
                .globalResponseMessage(RequestMethod.POST, errorList())
                .globalResponseMessage(RequestMethod.PUT, errorList())
                .globalResponseMessage(RequestMethod.DELETE, errorList())
                .select()
                .apis(RequestHandlerSelectors.basePackage(REST_PACKAGE))
                .paths(pathsFilter())
                .build()
                .apiInfo(metadata());
    }

    @Bean
    public EmailAnnotationPlugin emailPlugin() {
        return new EmailAnnotationPlugin();
    }

    private Predicate<String> pathsFilter() {
        return input ->
                input.matches(APP_PATH + "/account/.*") ||
                        input.matches(APP_PATH + "/doc/.*") ||
                        input.matches(APP_PATH + "/mail/.*") ||
                        input.matches(APP_PATH + "/password/.*") ||
                        input.matches(APP_PATH + "/register/.*") ||
                        input.matches(APP_PATH + "/logger/.*") ||
                        input.matches(APP_PATH + "/resource/.*");
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
        return newArrayList(
                new ResponseMessageBuilder().code(NOT_FOUND.value())
                        .responseModel(new ModelRef(getSimpleClassName(ErrorDetails.class)))
                        .message(ErrorDetails.toStringModel())
                        .build(),
                new ResponseMessageBuilder().code(INTERNAL_SERVER_ERROR.value())
                        .responseModel(new ModelRef(getSimpleClassName(ErrorDetails.class)))
                        .message(ErrorDetails.toStringModel())
                        .build()
                //             .responseModel(new ModelRef(getClassName(ErrorDetails.class))).build());
        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}