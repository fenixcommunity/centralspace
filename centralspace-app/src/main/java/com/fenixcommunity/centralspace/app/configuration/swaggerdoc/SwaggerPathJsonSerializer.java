package com.fenixcommunity.centralspace.app.configuration.swaggerdoc;

import io.swagger.models.Swagger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;

import java.util.List;

import static com.fenixcommunity.centralspace.utilities.common.Var.SLASH;

@Component
@Primary
public class SwaggerPathJsonSerializer extends JsonSerializer {

    public SwaggerPathJsonSerializer(List<JacksonModuleRegistrar> modules) {
        super(modules);
    }

    // to fix snapshot bug
    @Override
    public Json toJson(Object toSerialize) {
        if (toSerialize instanceof Swagger) {
            Swagger swagger = (Swagger) toSerialize;
            swagger.basePath(SLASH);
        }
        return super.toJson(toSerialize);
    }

}
