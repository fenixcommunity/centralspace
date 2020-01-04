package com.fenixcommunity.centralspace.app.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.fenixcommunity.centralspace.utilities.common.Var.LINE;
import static java.lang.String.format;

@Component
public class InitializerApp implements CommandLineRunner {

    @Value("${springfox.swagger2.host}")
    private String swagger2Host;

    @Override
    public void run(String... args) throws Exception {
        String appInfo = LINE + "Fenix community application has been launched" + LINE;
        String swaggerInfo = format("Please look at swager UI page:" + LINE +
                "http://%s/app/swagger-ui.html#/", swagger2Host);
        System.out.println(appInfo + swaggerInfo);
    }
}
