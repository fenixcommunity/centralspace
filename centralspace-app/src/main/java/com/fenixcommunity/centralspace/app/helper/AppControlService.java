package com.fenixcommunity.centralspace.app.helper;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.sun.net.httpserver.HttpServer;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class AppControlService {
    private final RestartEndpoint restartEndpoint;
    private final ContextRefresher contextRefresher;
    @Qualifier("prometheusHttpServer")
    private final HttpServer prometheusHttpServer;

    public void restartApp() {
        log.info("app restart start");
        prometheusHttpServer.stop(0);
        restartEndpoint.restart();
        log.info("app restarted");
    }

    public void refreshApp() {
        log.info("app refresh");
        contextRefresher.refresh();
        log.info("app refreshed");
    }
}
