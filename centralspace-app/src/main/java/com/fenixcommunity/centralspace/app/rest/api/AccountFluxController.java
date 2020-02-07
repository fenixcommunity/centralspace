package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper.mapToDto;
import static com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper.mapToJpa;
import static com.fenixcommunity.centralspace.utilities.common.Level.HIGH;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.security.SecuredUserDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/create") @ResponseStatus(HttpStatus.CREATED)
    @Secured({"FLUX_EDITOR"})
    public Mono<AccountDto> createFlux(@Valid @RequestBody final AccountDto accountDto) {
//         Mono<Void> or Flux<AccountDto>
        final Account createdAccount = mapToJpa(accountDto);
        final Long generatedId = accountService.save(createdAccount).getId();
        createdAccount.setId(generatedId);
todo        test it
        return Mono.just(mapToDto(createdAccount, HIGH))
                .doOnEach(accountDtoSignal -> System.out.println(accountDtoSignal.get().getLogin()))
                .defaultIfEmpty(AccountDto.builder().build())
                .log("AccountFluxController", Level.INFO, SignalType.ON_COMPLETE)
                .doOnError(throwable -> System.out.println(throwable.getMessage()));
//              .blockFirst if Flux
    }

    @PostMapping("/check-secured-user") @ResponseStatus(HttpStatus.OK)
    @Secured({"FLUX_EDITOR"})
    @PreFilter(value = "filterObject != authentication.principal.username", filterTarget = "usernames")
    public Mono<SecuredUserDto> postToCheckSecuredUser(List<String> usernames, List<String> roles) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        SecuredUserDto securedUserDto = new SecuredUserDto(authentication.getName(), authentication.getAuthorities().toString());
        if (!securedUserDto.getUsername().equals(usernames.stream().collect(Collectors.joining(";")))
                || !securedUserDto.getRole().equals(roles.stream().collect(Collectors.joining(";")))) {
            throw new ServiceFailedException("secure assertion fail");
        }
        return Mono.just(securedUserDto);
    }
//todo mapping and response in one line for other
    @GetMapping("/check-pre-post-authentication") @ResponseStatus(HttpStatus.OK)
    @Secured({"FLUX_GETTER"}) //   @Secured({ "FLUX_GETTER", "FLUX_EDITOR" })
    @PreAuthorize("#username == authentication.principal.username")
    @PostFilter("returnObject.username != authentication.principal.username")
    public SecuredUserDto getToCheckPreauthorize(String username) {
        return new SecuredUserDto(username, null);
    }

    //todo extend!

}
