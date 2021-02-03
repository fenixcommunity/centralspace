package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.utilities.common.Var.NEW_LINE;
import static lombok.AccessLevel.PRIVATE;

import java.util.StringJoiner;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fenixcommunity.centralspace.app.configuration.security.SecurityUserGroup;
import com.fenixcommunity.centralspace.app.rest.dto.register.RegisterType;
import com.fenixcommunity.centralspace.app.rest.dto.register.RegistrationRestRequest;
import com.fenixcommunity.centralspace.app.rest.dto.register.RegistrationRestRequest.AdminRegisterRegistration;
import com.fenixcommunity.centralspace.app.rest.dto.register.RegistrationRestRequest.StandardRegisterRegistration;
import com.fenixcommunity.centralspace.app.rest.dto.register.RestContactDetails;
import com.fenixcommunity.centralspace.app.rest.dto.register.RestFilledRegisterForm;
import com.fenixcommunity.centralspace.app.rest.dto.responseinfo.RestRegisterResponse;
import com.fenixcommunity.centralspace.app.service.register.RegisterService;
import com.fenixcommunity.centralspace.app.service.security.user.CreateUserData;
import com.fenixcommunity.centralspace.utilities.web.browser.BrowserType;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController @RequestMapping(value = "/api/register")
@Validated
@Log4j2
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
class RegisterController {
    private final RegisterService registerService;

    @PostMapping(value = "/submit", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<RestRegisterResponse> registerSubmit(@Valid @RequestBody final RegistrationRestRequest registerProcess,
                                                               @RequestHeader final HttpHeaders headers,
                                                               final HttpServletRequest request) {
        SecurityUserGroup securityUserGroup = null;
        if (registerProcess instanceof StandardRegisterRegistration) {
            StandardRegisterRegistration standardRegister = (StandardRegisterRegistration) registerProcess;
            securityUserGroup = standardRegister.securityUserGroup;
        } else if (registerProcess instanceof AdminRegisterRegistration) {
            AdminRegisterRegistration adminRegister = (AdminRegisterRegistration) registerProcess;
            securityUserGroup = adminRegister.securityUserGroup;
        }
        final RestFilledRegisterForm filledRegisterForm = registerProcess.filledRegisterForm;
        final RestContactDetails contactDetails = filledRegisterForm.contactDetails;
        final CreateUserData createUserData = CreateUserData.builder()
                .username(filledRegisterForm.username)
                .mail(filledRegisterForm.mail)
                .password(filledRegisterForm.password)
                .roleGroupId(filledRegisterForm.roleGroupId)
                .securityUserGroup(securityUserGroup)
                .country(contactDetails.country)
                .phoneNumber(contactDetails.phoneNumber)
                .build();

        registerService.registerNewAccount(createUserData);

        final StringJoiner registrationInfo = getRegistrationInfo(registerProcess, headers, request);
        return ResponseEntity.ok(buildRegisterResponse(registrationInfo.toString()));
    }

    private StringJoiner getRegistrationInfo(final RegistrationRestRequest registerProcess, final HttpHeaders headers, final HttpServletRequest request) {
        final BrowserType browserType = registerProcess.requestInfo.getBrowserType();
        final StringJoiner registrationInfo = new StringJoiner("");
        registrationInfo.add("http://")
                .add(request.getServerName())
                .add("" + request.getServerPort())
                .add(request.getContextPath())
                .add(NEW_LINE)
                .add(browserType.getDescription())
                .add(NEW_LINE)
                .add(headers.toString());
        return registrationInfo;
    }

    private RestRegisterResponse buildRegisterResponse(final String info) {
        return RestRegisterResponse.builder()
                .infoMessage(info)
                .redirectionLink("linkredirection")
                .registerType(RegisterType.STANDARD)
                .build();
    }

}
