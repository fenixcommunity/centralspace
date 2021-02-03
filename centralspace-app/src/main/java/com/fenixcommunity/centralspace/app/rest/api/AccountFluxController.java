package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.utilities.common.OperationLevel.HIGH;
import static com.fenixcommunity.centralspace.utilities.common.OperationLevel.LOW;

import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.security.SecuredUserDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.rest.mapper.account.modelmapper.AccountModelMapper;
import com.fenixcommunity.centralspace.app.service.account.AccountHelper;
import com.fenixcommunity.centralspace.app.service.account.AccountService;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

@RestController @RequestMapping(value = "/api/account-flux", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor(access = AccessLevel.PACKAGE) @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class AccountFluxController {

    private final AccountService accountService;
    private final AccountHelper accountHelper;

    @GetMapping("/check-pre-post-authentication")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_FLUX_GETTER"}) //   @Secured({ "ROLE_FLUX_EDITOR", "ROLE_FLUX_GETTER" })
    @PreAuthorize("#securedUsername == authentication.principal.username")
    // @PreAuthorize("hasRole('ROLE_FLUX_EDITOR') or hasRole('ROLE_FLUX_GETTER')")
    // @RolesAllowed({ "ROLE_FLUX_EDITOR", "ROLE_FLUX_GETTER" })
    @PostAuthorize("returnObject.username.equals(authentication.principal.username)")
    public SecuredUserDto getToCheckPreauthorize(@RequestParam(value = "securedUsername", defaultValue = "FLUX_USER_BASIC") final String securedUsername) {
        return new SecuredUserDto(securedUsername, null);
    }

//todo mapping and response in one line for other
    //todo extend!

    @PostMapping("/create") @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_FLUX_EDITOR"})
    public Mono<AccountDto> createFlux(@Valid @RequestBody final AccountDto accountDto) {
//         Mono<Void> or Flux<AccountDto>
        final Account requestedAccount = accountHelper.mapFromDtoAndValidate(accountDto, LOW);
        final Long generatedId = accountService.createAccount(requestedAccount).getId();
        requestedAccount.setId(generatedId);
        return Mono.just(new AccountModelMapper(HIGH).mapToDto(requestedAccount))
                .doOnNext(dto -> System.out.println(dto.getLogin()))
                .defaultIfEmpty(AccountDto.builder().build())
                .log("AccountFluxController", Level.INFO, SignalType.ON_COMPLETE)
                .doOnError(throwable -> System.out.println(throwable.getMessage()));
//              .blockFirst if Flux
    }

    @PostMapping("/check-secured-user") @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_FLUX_EDITOR"})
    @PreFilter(value = "filterObject == authentication.principal.username", filterTarget = "securedUserNames")
    // after -> [FLUX_USER_BASIC]
    // Set instead List because .contains searching
    public Mono<SecuredUserDto> postToCheckSecuredUser(@NotNull @RequestParam(value = "securedUserNames") final Set<String> securedUserNames) {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        final SecuredUserDto securedUserDto = new SecuredUserDto(authentication.getName(), authentication.getAuthorities().toString());
        if (!securedUserNames.stream()
                .collect(Collectors.joining(";"))
                .contains(securedUserDto.getUsername())) {
            throw new ServiceFailedException("secure assertion fail");
        }
        return Mono.just(securedUserDto);
    }

}
