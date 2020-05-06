package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.logger.LoggerResponseDto;
import com.fenixcommunity.centralspace.app.rest.mapper.account.AccountMapper;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.app.service.mail.mailsender.MailServiceBean;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.utilities.async.AsyncFutureHelper;
import com.fenixcommunity.centralspace.utilities.common.OperationLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final AccountService accountService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/send-async-mail/{to}")
    public Mono<LoggerResponseDto> sendAsyncMail(@PathVariable("to") final String to) {
        mailServiceBean.sendBasicMail(to);
        return Mono.just(LoggerResponseDto.builder()
                .log("query log")
                .loggerType("ASYNC").build());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-future-accounts")
    public Mono<List<AccountDto>> getFutureAccounts() {
        final List<Account> accountsFirst = AsyncFutureHelper.get(accountService.findAll());

        final CompletableFuture<List<Account>> futureAccountsSecond = accountService.findAll();

        final AccountMapper accountMapper = new AccountMapper(OperationLevel.LOW);
        final List<AccountDto> responseAccountsFirst = accountMapper.mapToDtoList(accountsFirst);

        final CompletableFuture<Optional<Account>> futureAccountThird = accountService.findByLogin("LOGINQUERY");


        CompletableFuture.allOf(futureAccountsSecond, futureAccountThird).join();

        final List<AccountDto> results = new ArrayList<>();
        results.addAll(responseAccountsFirst);
        results.addAll(accountMapper.mapToDtoList(AsyncFutureHelper.get(futureAccountsSecond)));
        results.add(accountMapper.mapToDto(AsyncFutureHelper.get(futureAccountThird).orElseGet(null)));

        return Mono.just(results);
    }

//    todo https://www.baeldung.com/java-asynchronous-programming
}
