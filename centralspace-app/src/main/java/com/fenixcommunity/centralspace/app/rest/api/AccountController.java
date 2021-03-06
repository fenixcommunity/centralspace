package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.utilities.common.OperationLevel.HIGH;
import static com.fenixcommunity.centralspace.utilities.common.OperationLevel.LOW;
import static com.fenixcommunity.centralspace.utilities.web.WebTool.getCurrentURI;
import static com.fenixcommunity.centralspace.utilities.web.WebTool.prepareResponseHeaders;
import static java.util.Collections.singletonMap;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.CollectionUtils.isEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fenixcommunity.centralspace.app.service.account.AccountHelper;
import com.fenixcommunity.centralspace.utilities.async.AsyncFutureHelper;
import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.responseinfo.BasicResponse;
import com.fenixcommunity.centralspace.app.rest.exception.ResourceNotFoundException;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.rest.mapper.account.modelmapper.AccountModelMapper;
import com.fenixcommunity.centralspace.app.service.account.AccountService;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.utilities.common.OperationLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// todo swagger/postman
//todo decorator and strategy
@RestController @RequestMapping(value = "/api/account", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "Account Management System", description = "Operations to manage lifecycle of Accounts")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
@Validated
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DB_MANAGE')")
public class AccountController {
    //todo RepresentationModel when empty body and links, Resource when body and links,
    private final AccountService accountService;
    private final AccountHelper accountHelper;

    //todo zobacz inne narzedzia jpa repo
    // Optional

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> getById(@PathVariable(value = "id") @PositiveOrZero final Long id) throws ResourceNotFoundException {
        final AccountDto accountDto = findByIdAndMapToDto(id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .headers(prepareResponseHeaders(singletonMap("Custom-Header", String.valueOf(accountDto.getId()))))
                .body(accountDto);
    }
    @GetMapping(value = "/login/{login}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> getByLogin(@PathVariable(value = "login") @NotBlank(message = "not blank") @Size(max = 10) final String login)
            throws ResourceNotFoundException {
        final AccountDto accountDto = AsyncFutureHelper.get(accountService.findByLogin(login))
                .map(x -> new AccountModelMapper(HIGH).mapToDto(x))
                .orElse(null); // .orElseThrow(() -> new ResourceNotFoundException("Account not found for this login: " + login));
        if (accountDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .headers(prepareResponseHeaders(singletonMap("Custom-Header", String.valueOf(accountDto.getId()))))
                .body(accountDto);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = BasicResponse.class, message = "OK"),
            @ApiResponse(code = 204, message = "Empty accounts result"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, response = ServiceFailedException.class, message = "xxx"),
            @ApiResponse(code = 501, message = "Not implemented for given extraction type")
    })
    @ApiOperation(value = "Get all Accounts")
    public ResponseEntity<List<AccountDto>> getAll(@RequestHeader HttpHeaders headers) {
        headers.add("headerAppRest", "getAllAccounts");
        final List<Account> accounts = AsyncFutureHelper.get(accountService.findAll());
        //   fast map <Long, Account> -> AbstractLong2ObjectMap<Account> bigAndGoodPerformanceList = new Long2ObjectOpenHashMap<>();
        //todo password add
        if (isEmpty(accounts)) {
            return ResponseEntity.noContent().build();
        }
        final AccountModelMapper accountModelMapper = new AccountModelMapper(OperationLevel.LOW);
        final List<AccountDto> responseAccounts = accountModelMapper.mapToDtoList(accounts);
        return ResponseEntity.ok(responseAccounts);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BasicResponse> create(@Valid @RequestBody final AccountDto accountDto) {
        final Account requestedAccount = accountHelper.mapFromDtoAndValidate(accountDto, LOW);
        accountService.createAccount(requestedAccount);
        final BasicResponse response = BasicResponse.builder().description("It's ok").status("PROCESSED").build();
        return ResponseEntity.created(getCurrentURI()).body(response);
    }

    //    todo RestErrorHandler apply
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicResponse> update(
            @PathVariable(name = "id") final Long id, @Valid @RequestBody final AccountDto accountDto)
            throws ResourceNotFoundException {
        isRecordExistElseThrowEx(id);
        final Account requestedAccount = accountHelper.mapFromDtoAndValidate(accountDto, LOW);
        final Account updatedAccount = accountService.createAccount(requestedAccount);
        final BasicResponse response = BasicResponse.builder().description("It's ok, accountID: " + updatedAccount.getId()).status("PROCESSED").build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity delete(@PathVariable(value = "id") final Long id) throws ResourceNotFoundException {
        return accountService.findById(id).map(
                a -> {
                    accountService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + id));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmMessage(@PathVariable(value = "id") final String id) {
        return ResponseEntity.ok(id);
    }

    private AccountDto findByIdAndMapToDto(final Long id) throws ResourceNotFoundException {
        return accountService.findById(id).map(x -> new AccountModelMapper(HIGH).mapToDto(x))
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + id));
    }

    //todo przenies
    private void isRecordExistElseThrowEx(final Long id) throws ResourceNotFoundException {
//todo        checkNotNull(id) or @Nullable?
        accountService.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + id));
    }
}
