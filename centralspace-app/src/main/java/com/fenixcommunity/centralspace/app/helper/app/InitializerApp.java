package com.fenixcommunity.centralspace.app.helper.app;

import static com.fenixcommunity.centralspace.utilities.common.Var.NEW_LINE;
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
    private final String graphiQLUrlPath;

    private final List<String> actuatorEndpoints = List.of(
            "auditevents", "beans", "conditions", "configprops", "env",
            "flyway", "health", "heapdump", "info", "liquibase", "logfile",
            "loggers", "metrics", "prometheus", "scheduledtasks", "sessions",
            "shutdown", "threaddump");

    public InitializerApp(@Value("${h2-console.host}") final String swagger2Host,
                          @Value("${springfox.swagger2.host}") final String h2Host,
                          @Value("${actuator.host}") final String actuatorHost,
                          @Value("${prometheus.url}") final String prometheusUrl,
                          @Value("${graphiql.urlPath}") final String graphiQLUrlPath) {
        this.swaggerEndpoint = format("%s/app/swagger-ui.html#/", swagger2Host);
        this.h2Endpoint = format("%s/app/h2-console/", h2Host);
        this.actuatorEndpoint = format("%s/app/actuator/", actuatorHost);
        this.prometheusEndpoint = prometheusUrl;
        this.graphiQLUrlPath = graphiQLUrlPath;
    }

    @Override
    public void run(String... args) {
        final String appInfo = "Fenix community application has been launched";

        final String pluginIdeInfo = "Please look at interesting IntelliJ plugins:" + NEW_LINE + "README.md file";

        final String swaggerInfo = "Please look at SWAGGER UI page:" + NEW_LINE + swaggerEndpoint;

        final String h2Info = "Please look at H2 database console:" + NEW_LINE + h2Endpoint;

        final String jProfilerInfo = "Please run app with JProfiler coverage and check JVM parameters";

        final String sonarServerInfo = "Please run SONAR analyzer console:" + NEW_LINE + "http://localhost:9000/dashboard?id=centralspace";

        final String sonarInfo = "Please run SONAR build maven:" + NEW_LINE + "mvn sonar:sonar" + NEW_LINE +
                "-Dsonar.projectKey=centralspace" + NEW_LINE +
                "-Dsonar.host.url=http://localhost:9000" + NEW_LINE +
                "-Dsonar.login=1ea3c73b39a7f3aa2e4862bb874c8fbca7895943";

        final StringJoiner actuatorInfo = new StringJoiner(NEW_LINE)
                .add("Please look at spring actuator app live status:");
        actuatorEndpoints.forEach(endpoint -> actuatorInfo.add(actuatorEndpoint + endpoint));

        final String prometheusInfo = "Please run prometheus metrics server:" + NEW_LINE + prometheusEndpoint;

        final String vaultInfo = "Please run vault server:" + NEW_LINE + "vault server --dev --dev-root-token-id=\"00000000-0000-0000-0000-000000000000\""
                + NEW_LINE + "and run command: set VAULT_ADDR=http://127.0.0.1:8200"
                + NEW_LINE + "and put keys: vault kv put secret/centralspace-vault-config aws.app.keyId=xxx aws.app.secretKey=xxx"
                + NEW_LINE + "and get/delete keys: vault kv get secret/centralspace-vault-config" + NEW_LINE + "or go to: http://localhost:8200/ui";

        final String testInfo = "Please run tests with profile 'test'(run with env. variables -> spring.profiles.active=test)";
        final String analyzeVMInfo = "Analyze VM working (the same provided by Prometheus server. Not for Open JDK)"
                + NEW_LINE + "-XX:NativeMemoryTracking=summary"
                + NEW_LINE + "command: jcmd -> jcmd <pid> VM.native_memory";

        final String graphQLInfo = "Please test graphQL queries: " + NEW_LINE
                + "Login and go to query console: " + graphiQLUrlPath + NEW_LINE + "Schema info:" + graphiQLUrlPath + "/schema.json";

        log.info(new StringJoiner(SEPARATOR)
                .add(appInfo)
                .add(pluginIdeInfo)
                .add(h2Info)
                .add(jProfilerInfo)
                .add(sonarServerInfo)
                .add(sonarInfo)
                .add(actuatorInfo.toString())
                .add(prometheusInfo)
                .add(vaultInfo)
                .add(testInfo)
                .add(analyzeVMInfo)
                .add(graphQLInfo)
                .add(NEW_LINE)
                .add(swaggerInfo)
                .add(NEW_LINE)
                .toString());
    }
}
