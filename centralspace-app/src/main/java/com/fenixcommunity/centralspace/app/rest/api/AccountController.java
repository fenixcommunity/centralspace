package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.exception.rest.ResourceNotFoundException;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.domain.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
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

import javax.validation.Valid;
import java.util.List;

import static com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper.mapToJpa;
import static com.fenixcommunity.centralspace.utilities.common.Level.HIGH;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// todo swagger/postman
@RestController
@RequestMapping(value = "/account", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountController {
    //todo RepresentationModel when empty body and links, Resource when body and links,
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //todo zobacz inne narzedzia jpa repo
    // Optional

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        AccountDto accountDto = findByIdAndMapToDto(id);
        accountDto.add(linkTo(methodOn(AccountController.class).confirmMessage(id.toString())).withSelfRel());
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accounts = accountService.findAll();
        //todo AccountDto
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel create(@Valid @RequestBody AccountDto accountDto) {
        Account createdAccount = mapToJpa(accountDto);
        Long generatedId = accountService.save(createdAccount).getId();
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(methodOn(AccountController.class).confirmMessage(generatedId.toString())).withSelfRel());
        return response;
    }

    //    todo RestErrorHandler apply
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel update(@PathVariable(name = "id") Long id, @Valid @RequestBody Account requestAccount) throws ResourceNotFoundException {
        isRecordExistElseThrowEx(id);
        //todo account exist
        final Account updatedAccount = accountService.save(requestAccount);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(methodOn(AccountController.class).confirmMessage(updatedAccount.getId().toString())).withSelfRel());
        return response;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return accountService.findById(id).map(
                a -> {
                    accountService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + id));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmMessage(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok(id);
    }

    private AccountDto findByIdAndMapToDto(Long id) throws ResourceNotFoundException {
        return accountService.findById(id).map(x -> AccountMapper.mapToDto(x, HIGH))
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + id));
    }

    //todo przenies
    private void isRecordExistElseThrowEx(Long id) throws ResourceNotFoundException {
        accountService.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Account not found for this id: " + id));
    }
}
