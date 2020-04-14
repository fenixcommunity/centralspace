package com.fenixcommunity.centralspace.app.utils;

import static com.fenixcommunity.centralspace.utilities.common.Var.LINE;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
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
    private final String swaggerEndpoint;
    private final String h2Endpoint;
    private final String actuatorEndpoint;

    private final List<String> actuatorEndpoints = List.of(
            "auditevents", "beans", "conditions", "configprops", "env",
            "flyway", "health", "heapdump", "info", "liquibase", "logfile",
            "loggers", "metrics", "prometheus", "scheduledtasks", "sessions",
            "shutdown", "threaddump");

    public InitializerApp(@Value("${h2-console.host}") String swagger2Host,
                          @Value("${springfox.swagger2.host}") String h2Host,
                          @Value("${actuator-console.host}") String actuatorHost) {
        this.swaggerEndpoint = format("%s/app/swagger-ui.html#/", swagger2Host);
        this.h2Endpoint = format("%s/app/h2-console/", h2Host);
        this.actuatorEndpoint = format("%s/app/actuator/", actuatorHost);
    }

    @Override
    public void run(String... args) throws Exception {
        final String appInfo = "Fenix community application has been launched";

        final String pluginIdeInfo = "Please look at interesting IntelliJ plugins:" + LINE + "README.md file";

        final String swaggerInfo = "Please look at SWAGGER UI page:" + LINE + swaggerEndpoint;

        final String h2Info = "Please look at H2 database console:" + LINE + h2Endpoint;

        final String sonarServerInfo = "Please run SONAR analyzer console:" + LINE + "http://localhost:9000/dashboard?id=centralspace";

        final String sonarInfo = "Please run SONAR build maven:" + LINE + "mvn sonar:sonar" + LINE +
                "-Dsonar.projectKey=centralspace" + LINE +
                "-Dsonar.host.url=http://localhost:9000" + LINE +
                "-Dsonar.login=1ea3c73b39a7f3aa2e4862bb874c8fbca7895943";

        final StringJoiner actuatorInfo = new StringJoiner(LINE)
                .add("Please look at spring actuator app live status:");
        actuatorEndpoints.forEach(endpoint -> actuatorInfo.add(actuatorEndpoint + endpoint));

        log.info(new StringJoiner(LINE)
                .add(appInfo)
                .add(pluginIdeInfo)
                .add(swaggerInfo)
                .add(h2Info)
                .add(sonarServerInfo)
                .add(sonarInfo)
                .add(actuatorInfo.toString())
                .toString());
    }
}
