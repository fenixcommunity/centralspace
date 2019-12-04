package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.rest.dto.register.RegisterType;
import com.fenixcommunity.centralspace.app.rest.dto.register.RestRegisterProcess;
import com.fenixcommunity.centralspace.app.rest.dto.responseinfo.RestRegisterResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/register")
@Log4j2
class RegisterController {

    @PostMapping(value = "/submit/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<RestRegisterResponse> registerSubmit(@RequestBody RestRegisterProcess registerProcess,
                                                               @NotNull @PathVariable("id") Long registerId,
                                                               @RequestHeader HttpHeaders headers) {
        log.info(registerProcess.cookies.getBrowserType().name());
        RestRegisterResponse payload = buildRegisterResponse(headers.toString());
//   TODO     tutaj serwis z obserwatorem wysylajacego maila do administracji. Globalny serwis informujacy o zmianie
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
    //todo regex

    private RestRegisterResponse buildRegisterResponse(String info) {
        RestRegisterResponse response = new RestRegisterResponse(info, "linkredirection", RegisterType.STANDARD);
        //todo ...
        return response;
    }

}
