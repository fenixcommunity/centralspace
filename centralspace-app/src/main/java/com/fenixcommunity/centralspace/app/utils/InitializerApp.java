package com.fenixcommunity.centralspace.app.utils;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.fenixcommunity.centralspace.utilities.common.Var.LINE;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InitializerApp implements CommandLineRunner {

    private final String swagger2Host;

    private final String h2Host;

    public InitializerApp(@Value("${h2-console.host}") String swagger2Host,
                          @Value("${springfox.swagger2.host}") String h2Host) {
        this.swagger2Host = swagger2Host;
        this.h2Host = h2Host;
    }

    @Override
    public void run(String... args) throws Exception {
        String appInfo = LINE + "Fenix community application has been launched";
        String swaggerInfo = format("Please look at swager UI page:" + LINE +
                "http://%s/app/swagger-ui.html#/", swagger2Host);
        String h2Info = format("Please look at h2 database console:" + LINE +
                "http://%s/app/h2-console", h2Host);
        System.out.println(appInfo + LINE + swaggerInfo + LINE + h2Info + LINE);
    }
}
