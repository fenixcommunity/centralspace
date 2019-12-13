package com.fenixcommunity.centralspace.app.utils;

import com.fenixcommunity.centralspace.utilities.common.Var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class InitializerApp implements CommandLineRunner {

    @Value("${springfox.swagger2.host}")
    private String swagger2Host;

    @Override
    public void run(String... args) throws Exception {
        String appInfo = "Fenix community application has been launched" + Var.LINE;
        String swaggerInfo = format("Please look at swager UI page:" + Var.LINE +
                        " http://localhost:%s/api/swagger-ui.html#/", swagger2Host);
        System.out.println(appInfo + swaggerInfo);
    }
}
