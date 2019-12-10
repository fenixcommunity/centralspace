package com.fenixcommunity.centralspace.app.rest.api;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logger")
@Log4j2
public class LoggingController {


    @GetMapping("/")
    public String run() {
        log.trace("TRACE Message");
        log.debug("DEBUG Message");
        log.info("INFO Message");
        log.warn("WARN Message");
        log.error("ERROR Message");

        return "DONE";
    }
}
