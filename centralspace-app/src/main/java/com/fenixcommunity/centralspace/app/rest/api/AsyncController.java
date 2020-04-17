package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.rest.dto.logger.LoggerResponseDto;
import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailServiceBean;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController @RequestMapping("/api/async")
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true) @AllArgsConstructor(access = PACKAGE)
public class AsyncController {
    private final MailServiceBean mailServiceBean;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/send-async-mail/{to}")
    public Mono<LoggerResponseDto> sendAsyncMail(@PathVariable("to") final String to) {
        mailServiceBean.sendBasicMail(to);
        return Mono.just(LoggerResponseDto.builder()
                .log("query log")
                .loggerType("ASYNC").build());
    }

    FUTURE https://www.baeldung.com/spring-async
    todo https://www.baeldung.com/java-asynchronous-programming
}
