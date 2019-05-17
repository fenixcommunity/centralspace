package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.rest.dto.register.RegisterType;
import com.fenixcommunity.centralspace.app.rest.dto.register.RestRegisterProcess;
import com.fenixcommunity.centralspace.app.rest.dto.responseinfo.RestRegisterResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/register")
@Log
class RegisterController {

    @PostMapping(value = "/submit/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<RestRegisterResponse> registerSubmit(@RequestBody RestRegisterProcess registerProcess,
                                                               @NotNull @PathVariable("id") Long registerId,
                                                               @RequestHeader HttpHeaders headers) {
        log.info(registerProcess.cookies.getBrowserType().name());
        RestRegisterResponse payload = buildRegisterResponse("info");
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
    //todo regex
    //todo @LogParameter

    private RestRegisterResponse buildRegisterResponse(String info) {
        RestRegisterResponse response = new RestRegisterResponse(info, "linkredirection", RegisterType.STANDARD);
        // ...
        return response;
    }

}
