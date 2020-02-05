package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper.mapToDto;
import static com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper.mapToJpa;
import static com.fenixcommunity.centralspace.utilities.common.Level.HIGH;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.Valid;
import java.util.logging.Level;

import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

@RestController @RequestMapping(value = "/api/account-flux", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class AccountFluxController {

    private final AccountService accountService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDto> createFlux(@Valid @RequestBody final AccountDto accountDto) {
//         Mono<Void> or Flux<AccountDto>
        final Account createdAccount = mapToJpa(accountDto);
        final Long generatedId = accountService.save(createdAccount).getId();
        createdAccount.setId(generatedId);
        return Mono.just(mapToDto(createdAccount, HIGH))
                .doOnEach(accountDtoSignal -> System.out.println(accountDtoSignal.get().getLogin()))
                .defaultIfEmpty(AccountDto.builder().build())
                .log("AccountFluxController", Level.INFO, SignalType.ON_COMPLETE)
                .doOnError(throwable -> System.out.println(throwable.getMessage()));
//              .blockFirst if Flux
    }

    //todo extend!

}
