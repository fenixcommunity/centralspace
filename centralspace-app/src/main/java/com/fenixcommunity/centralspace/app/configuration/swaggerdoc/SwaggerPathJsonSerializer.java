package com.fenixcommunity.centralspace.app.configuration.swaggerdoc;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import io.swagger.models.Swagger;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;

@Component
@Primary
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SwaggerPathJsonSerializer extends JsonSerializer {

    public SwaggerPathJsonSerializer(final List<JacksonModuleRegistrar> modules) {
        super(modules);
    }

    @Override
    public Json toJson(final Object toSerialize) {
        if (toSerialize instanceof Swagger) {
            final Swagger swagger = (Swagger) toSerialize;
//          to fix 3.0.0 snapshot bug
//          swagger.basePath(SLASH);
        }
        return super.toJson(toSerialize);
    }

}
