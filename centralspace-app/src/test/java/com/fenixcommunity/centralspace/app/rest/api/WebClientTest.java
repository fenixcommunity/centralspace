package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.configuration.CentralspaceApplicationConfig;
import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.app.configuration.restcaller.webclient.WebClientConfig;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.logger.LoggerDto;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.ADMIN;
import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.utilities.common.Var.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.util.Assert.isInstanceOf;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,
        classes = {CentralspaceApplicationConfig.class, WebClientConfig.class})
//todo replace from CentralspaceApplicationConfig to CentralspaceApplicationConfigTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WebClientTest {

    private static final String BASE_ACCOUNT_URL = "/api/account/";
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
//              .bindToController(new TestController()) -> custom controller
                .filter(basicAuthentication(user, PASSWORD))
                .build();
        webTestClient.options()
                .accept(MediaType.ALL)
                .headers(httpHeaders -> {
                    httpHeaders.setDate(ZonedDateTime.now());
                })
                .cookies(cookie -> cookie.add("cookieTest", "cookieValue"));
//      webTestClient
//              .mutateWith(authentication(token))
//              .mutateWith(csrf())
        return webTestClient;
    }

    private void initAccount() {
        Account account = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL)
                .passwords(Collections.singletonList(null))
                .build();
        accountDto = new AccountDto(1L, "sdds", "mad@o2.pl");
        when(accountService.findById(ID)).thenReturn(Optional.of(account));
        when(accountService.save(any(Account.class))).thenReturn(account); // -> or  eq(mapToJpa(accountDto))
    }

    @Test
    void testBeans() {
        assertNotNull(adminClient);
        assertNotNull(basicClient);
        assertNotNull(restCallerStrategy.buildWebClient());
    }

    @Test
    void testLoggerAsBasic() {
        basicClient.get()
                .uri(BASE_LOGGER_URL + "run")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .expectBody()
                .jsonPath("$.message").isEqualTo("error"); // if array [0].message
    }

    refactoring
    @Test

    void testLoggerAsBasic2() {
        Mono<LoggerDto> monoo = Mono.just(new LoggerDto("b", "z"));
        LoggerDto result = restCallerStrategy.buildWebClient().post()
                .uri("http://localhost:" + port + APP_PATH + BASE_LOGGER_URL + "post")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .bodyValue(new LoggerDto("b", "z"))
                .exchange().flatMap(map -> map.bodyToMono(LoggerDto.class))
                .block();
        System.out.println(result.toString());
    }

    @Test
    void testLoggerAsBasic3() {
        Mono<LoggerDto> monoo = Mono.just(new LoggerDto("b", "z"));
        adminClient.post()
                .uri("http://localhost:" + port + APP_PATH + BASE_LOGGER_URL + "post")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .bodyValue(new LoggerDto("g", "i"))
                .exchange().expectBody().jsonPath("$.loggerType").isEqualTo("s");
    }

    webclienttest ->

    @Test
    void testAccountCreateCallAsAdmin() {
        AccountDto result2 = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(ExchangeFilterFunctions
                        .basicAuthentication(ADMIN.name(), PASSWORD)).build()
                .post()
                .uri("http://localhost:" + port + APP_PATH + BASE_ACCOUNT_URL + "create-flux")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .bodyValue(accountDto)
                .exchange().flatMap(map -> map.bodyToMono(AccountDto.class)).block();
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
        System.out.println(result2);
    }

    /*      @Test(expected = WebClientResponseException.class)
            or exceptionRule -> junit4*/
    @Test
    void testWebClientExceptionByRealWebClient() {
        WebClientException exception = assertThrows(WebClientException.class, () -> {
            WebClient.create()
                    .get()
                    .uri("http://localhost:" + port + APP_PATH + BASE_ACCOUNT_URL + "all")
                    .headers(httpHeaders -> httpHeaders.setBearerAuth(PASSWORD))
                    .retrieve()
                    .bodyToMono(Account.class)
                    .block();
        });
        isInstanceOf(WebClientResponseException.class, exception);
        WebClientResponseException webClientException = (WebClientResponseException) exception;
        isTrue(webClientException.getStatusCode() == HttpStatus.UNAUTHORIZED);


    }
}
