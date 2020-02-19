package com.fenixcommunity.centralspace.app.utils;

import static com.fenixcommunity.centralspace.utilities.common.Var.LINE;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import java.util.StringJoiner;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
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
        String appInfo = "Fenix community application has been launched";
        String pluginIdeInfo = "Please look at interesting plugins:" + LINE +
                "README.md file";
        String swaggerInfo = format("Please look at swager UI page:" + LINE +
                "http://%s/app/swagger-ui.html#/", swagger2Host);
        String h2Info = format("Please look at h2 database console:" + LINE +
                "http://%s/app/h2-console", h2Host);
        String sonarServerInfo = "Please run sonar analyzer console:" + LINE +
                "http://localhost:9000/dashboard?id=centralspace";
        String sonarInfo = "Please run sonar build maven:" + LINE + "mvn sonar:sonar" + LINE +
                "-Dsonar.projectKey=centralspace" + LINE +
                "-Dsonar.host.url=http://localhost:9000" + LINE +
                "-Dsonar.login=1ea3c73b39a7f3aa2e4862bb874c8fbca7895943";

        log.info(new StringJoiner(LINE)
                .add(appInfo)
                .add(pluginIdeInfo)
                .add(swaggerInfo)
                .add(h2Info)
                .add(sonarServerInfo)
                .add(sonarInfo).toString());
    }

    http://localhost:9000/dashboard?id=centralspace
    todo +
    read more
    https://www.baeldung.com/sonar-qube  -> OpenJDK 11 or Oracle JRE 11

    mvn sonar:sonar
            -Dsonar.projectKey=centralspace
            -Dsonar.host.url=http://localhost:9000
            -Dsonar.login=1ea3c73b39a7f3aa2e4862bb874c8fbca7895943
    zobacz co potrafi Intellij i taby, widoki. Code analizer
}
