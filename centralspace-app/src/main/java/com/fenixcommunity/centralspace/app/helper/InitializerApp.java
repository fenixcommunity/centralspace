package com.fenixcommunity.centralspace.app.helper;

import static com.fenixcommunity.centralspace.utilities.common.Var.LINE;
import static com.fenixcommunity.centralspace.utilities.common.Var.SEPARATOR;
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
    private final String prometheusEndpoint;

    private final List<String> actuatorEndpoints = List.of(
            "auditevents", "beans", "conditions", "configprops", "env",
            "flyway", "health", "heapdump", "info", "liquibase", "logfile",
            "loggers", "metrics", "prometheus", "scheduledtasks", "sessions",
            "shutdown", "threaddump");

    public InitializerApp(@Value("${h2-console.host}") final String swagger2Host,
                          @Value("${springfox.swagger2.host}") final String h2Host,
                          @Value("${actuator.host}") final String actuatorHost,
                          @Value("${prometheus.url}") final String prometheusUrl) {
        this.swaggerEndpoint = format("%s/app/swagger-ui.html#/", swagger2Host);
        this.h2Endpoint = format("%s/app/h2-console/", h2Host);
        this.actuatorEndpoint = format("%s/app/actuator/", actuatorHost);
        this.prometheusEndpoint = prometheusUrl;
    }

    @Override
    public void run(String... args) {
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

        final String prometheusInfo = "Please run prometheus metrics server:" + LINE + prometheusEndpoint;

        final String vaultInfo = "Please run vault server:" + LINE + "vault server --dev --dev-root-token-id=\"00000000-0000-0000-0000-000000000000\""
                + LINE + "and run command: set VAULT_ADDR=http://127.0.0.1:8200"
                + LINE + "and put keys: vault kv put secret/centralspace-vault-config aws.app.keyId=xxx aws.app.secretKey=xxx"
                + LINE + "and get/delete keys: vault kv get secret/centralspace-vault-config" + LINE + "or go to: http://localhost:8200/ui";

        final String testInfo = "Please run tests with profile 'test'(run with env. variables -> spring.profiles.active=test)";
        final String analyzeVMInfo = "Analyze VM working (the same provided by Prometheus server. Not for Open JDK)"
                + LINE + "-XX:NativeMemoryTracking=summary"
                + LINE + "command: jcmd -> jcmd <pid> VM.native_memory";
        final String redisInfo = LINE;

        log.info(new StringJoiner(SEPARATOR)
                .add(appInfo)
                .add(pluginIdeInfo)
                .add(h2Info)
                .add(sonarServerInfo)
                .add(sonarInfo)
                .add(actuatorInfo.toString())
                .add(prometheusInfo)
                .add(vaultInfo)
                .add(testInfo)
                .add(analyzeVMInfo)
                .add(LINE+ swaggerInfo)
                .toString());
    }
}
