package com.fenixcommunity.centralspace.app.rest.api;

import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import com.fenixcommunity.centralspace.app.configuration.security.auto.handler.OnRegistrationCompleteEvent;
import com.fenixcommunity.centralspace.app.rest.dto.register.RegisterType;
import com.fenixcommunity.centralspace.app.rest.dto.register.RestRegisterProcess;
import com.fenixcommunity.centralspace.app.rest.dto.responseinfo.RestRegisterResponse;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController @RequestMapping("/api/register")
@Log4j2
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class RegisterController {

    private ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "/submit/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<RestRegisterResponse> registerSubmit(@RequestBody final RestRegisterProcess registerProcess,
                                                               @NotNull @PathVariable("id") final Long registerId,
                                                               @RequestHeader final HttpHeaders headers,
                                                               final HttpServletRequest request) {
        log.info(registerProcess.cookies.getBrowserType().name());
        final RestRegisterResponse payload = buildRegisterResponse(headers.toString());
//   TODO     tutaj serwis z obserwatorem wysylajacego maila do administracji. Globalny serwis informujacy o zmianie

//      TODO  final User registered = userService.registerNewUserAccount(userDto);
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(new User("username", "password", emptyList()), request.getLocale(), appUrl));
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
    //todo regex
    private RestRegisterResponse buildRegisterResponse(final String info) {
        //todo ...
        return RestRegisterResponse.builder()
                .infoMessage(info)
                .redirectionLink("linkredirection")
                .registerType(RegisterType.STANDARD).build();
    }

}
