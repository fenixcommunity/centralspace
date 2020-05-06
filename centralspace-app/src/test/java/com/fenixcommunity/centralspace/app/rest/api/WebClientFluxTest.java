package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.app.configuration.security.auto.SecurityRole.ADMIN;
import static com.fenixcommunity.centralspace.app.configuration.security.auto.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.app.configuration.security.auto.SecurityRole.FLUX_EDITOR;
import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import java.time.ZonedDateTime;
import java.util.Optional;

import com.fenixcommunity.centralspace.app.configuration.CentralspaceApplicationConfig;
import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.logger.LoggerQueryDto;
import com.fenixcommunity.centralspace.app.rest.dto.logger.LoggerResponseDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.rest.mapper.account.AccountMapper;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.utilities.common.OperationLevel;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {CentralspaceApplicationConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WebClientLuxTest {

    private static final String BASE_ACCOUNT_FLUX_URL = "/api/account-flux/";
    private static final String BASE_LOGGER_URL = "/api/logger/";
    private static final String APP_PATH = "/app";

    private WebTestClient adminClient;
    private WebTestClient basicClient;

    private AccountDto accountDto;

    @Autowired
    private RestCallerStrategy restCallerStrategy;

    @LocalServerPort
    private String port;

    @MockBean
    private AccountService accountService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeEach
    public void init() {
        this.basicClient = setOptions(BASIC.name());
        this.adminClient = setOptions(ADMIN.name());
        initAccount();
    }

    private WebTestClient setOptions(String user) {
        WebTestClient webTestClient = WebTestClient
                .bindToServer().baseUrl("http://localhost:" + port + APP_PATH)
                .filter(basicAuthentication(user, PASSWORD))
                .build();
        webTestClient.options()
                .accept(MediaType.ALL)
                .headers(httpHeaders -> {
                    httpHeaders.setDate(ZonedDateTime.now());
                })
                .cookies(cookie -> cookie.add("cookieTest", "cookieValue"))
                .ifModifiedSince(ZonedDateTime.now());
        return webTestClient;
    }

    @Test
    void testBeans() {
        assertNotNull(adminClient);
        assertNotNull(basicClient);
        assertNotNull(restCallerStrategy.getWebClient());
    }

    private void initAccount() {
        Account account = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL)
                .address(new Address(8L, "Poland", "Cracow", null))
                .build();
        accountDto = new AccountMapper(OperationLevel.HIGH).mapToDto(account);
        when(accountService.findById(ID)).thenReturn(Optional.of(account));
        when(accountService.save(any(Account.class))).thenReturn(account); // -> or  eq(mapToJpa(accountDto))
    }

    @Test
    void testLoggerAsBasic() {
        LoggerResponseDto loggerResponseDto = restCallerStrategy.getWebClient().post()
                .uri("http://localhost:" + port + APP_PATH + BASE_LOGGER_URL + "query")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .bodyValue(new LoggerQueryDto("this is the test log", "TEST"))
                .exchange().flatMap(map -> map.bodyToMono(LoggerResponseDto.class))
                .blockOptional().orElseGet(() -> {
                    throw new ServiceFailedException("message");
                });
        assertNotNull(loggerResponseDto);

        assertEquals("query log", loggerResponseDto.getLog());
    }

    @Test
    void testLoggerQueryByWebTestClient() {
        adminClient.post()
                .uri("http://localhost:" + port + APP_PATH + BASE_LOGGER_URL + "query")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .bodyValue(new LoggerQueryDto("Get server acount id 3 document log", "TEST"))
                .exchange()
                .expectBody().jsonPath("$.log").isEqualTo("query log");
    }

    @Test
    void testAccountFluxCreateCallAsAdmin() {
        AccountDto result = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(ExchangeFilterFunctions
                        .basicAuthentication(FLUX_EDITOR.name(), PASSWORD)).build()
                .post()
                .uri("http://localhost:" + port + APP_PATH + BASE_ACCOUNT_FLUX_URL + "create") // or builder if queryParam
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .bodyValue(accountDto)
//              .body(BodyInserters.fromMultipartData(new LinkedMultiValueMap()) // add(k,v)
//                        BodyInserters.fromObject(new Long(2)) );
//                        Mono.just(...,)
//                    .bodyValue(javaObj)
                .exchange().flatMap(map -> map.bodyToMono(AccountDto.class)).block();
//               .expectHeader().contentType(MediaType.APPLICATION_JSON);

        assertNotNull(result);
        assertEquals(ID, result.getId());
    }
}
