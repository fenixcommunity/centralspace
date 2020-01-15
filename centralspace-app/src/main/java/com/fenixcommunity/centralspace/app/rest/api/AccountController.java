package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.responseinfo.BasicResponse;
import com.fenixcommunity.centralspace.app.rest.exception.ResourceNotFoundException;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper.mapToJpa;
import static com.fenixcommunity.centralspace.utilities.common.Level.HIGH;
import static com.fenixcommunity.centralspace.utilities.web.WebTool.prepareResponseHeaders;
import static java.util.Collections.singletonMap;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

// todo swagger/postman
//todo decorator and strategy
@RestController
@RequestMapping(value = "/api/account", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "Account Management System", description = "Operations to manage lifecycle of Accounts")
//TODO @PreAuthorize("hasAuthority('ROLE_USER')")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class AccountController {
    //todo RepresentationModel when empty body and links, Resource when body and links,
    private final AccountService accountService;

    //todo zobacz inne narzedzia jpa repo
    // Optional

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> getById(@PathVariable(value = "id") final Long id) throws ResourceNotFoundException {
        final AccountDto accountDto = findByIdAndMapToDto(id);
        singletonMap("Custom-Header", accountDto.getId());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .headers(prepareResponseHeaders(singletonMap("Custom-Header", accountDto.getId())))
                .body(accountDto);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = BasicResponse.class, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, response = ServiceFailedException.class, message = "xxx"),
            @ApiResponse(code = 501, message = "Not implemented for given extraction type")
    })
    @ApiOperation(value = "Get all Accounts")
    public ResponseEntity<List<Account>> getAll() {
        final List<Account> accounts = accountService.findAll();
        //todo password add
        //todo AccountDto
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BasicResponse> create(@Valid @RequestBody final AccountDto accountDto) {
        final Account createdAccount = mapToJpa(accountDto);
        final Long generatedId = accountService.save(createdAccount).getId();
        final BasicResponse response = BasicResponse.builder().description("It's ok").status("PROCESSED").build();
        return ResponseEntity.created(getCurrentURI()).body(response);
    }

    //    todo RestErrorHandler apply
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BasicResponse> update(
            @PathVariable(name = "id") final Long id, @Valid @RequestBody final Account requestAccount)
            throws ResourceNotFoundException {
        isRecordExistElseThrowEx(id);
        //todo account exist
        final Account updatedAccount = accountService.save(requestAccount);
        final BasicResponse response = BasicResponse.builder().description("It's ok, accountID: " + updatedAccount.getId()).status("PROCESSED").build();
        return ResponseEntity.created(getCurrentURI()).body(response);
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
        return accountService.findById(id).map(x -> AccountMapper.mapToDto(x, HIGH))
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + id));
    }

    //todo przenies
    private void isRecordExistElseThrowEx(final Long id) throws ResourceNotFoundException {
//todo        checkNotNull(id) or @Nullable?
        accountService.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + id));
    }

    //todo remove?
    private URI getCurrentURI() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    }
}
