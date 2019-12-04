package com.fenixcommunity.centralspace.app.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logger")
public class LoggingController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping("/")
    public String run() {
        logger.trace("TRACE Message");
        logger.debug("DEBUG Message");
        logger.info("INFO Message");
        logger.warn("WARN Message");
        logger.error("ERROR Message");

        return "DONE";
    }
}
