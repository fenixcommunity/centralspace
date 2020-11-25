package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.service.batch.BatchLauncher;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController @RequestMapping("/api/batch")
@Log4j2
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class BatchController {

    private final BatchLauncher batchLauncher;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/account")
    public Mono<String> runAccountBatch() {
        final BatchStatus batchStatus = batchLauncher.launchAccountBatch();
        return Mono.just(batchStatus.name());
    }
}
