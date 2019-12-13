package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.exception.rest.ErrorDetails;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.randomString;

@RestController
@RequestMapping("/logger")
@Log4j2
public class LoggingController {

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = ErrorDetails.class, message = "OK"),
            @ApiResponse(code = 400, response = ErrorDetails.class, message = "Bad request"),
            @ApiResponse(code = 404, response = ErrorDetails.class, message = "Not found"),
            @ApiResponse(code = 500, response = ErrorDetails.class, message = "Internal server error"),
            @ApiResponse(code = 501, response = ErrorDetails.class, message = "Not implemented for given extraction type")
    })
    @GetMapping("/run")
    public ResponseEntity<ErrorDetails> runLogsAndRegisterErrorDetails() {
        log.trace("TRACE Message");
        log.debug("DEBUG Message");
        log.info("INFO Message");
        log.warn("WARN Message");
        log.error("ERROR Message");

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(ZonedDateTime.now())
                .message("error")
                .details("details")
                .logRef(randomString()).build();
        return ResponseEntity.ok(errorDetails);
    }
}
