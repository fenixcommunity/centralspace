package com.fenixcommunity.centralspace.app.rest.api;

import com.fenixcommunity.centralspace.app.configuration.CentralspaceApplicationConfig;
import com.fenixcommunity.centralspace.app.configuration.restcaller.RestCallerStrategy;
import com.fenixcommunity.centralspace.app.configuration.restcaller.webclient.WebClientConfig;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.ADMIN;
import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,
        classes = {CentralspaceApplicationConfig.class, WebClientConfig.class})
//todo replace from CentralspaceApplicationConfig to CentralspaceApplicationConfigTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WebClientTest {

    private static final String BASE_ACCOUNT_URL = "/api/account/";
    private static final String BASE_LOGGER_URL = "/api/logger/";

    private WebTestClient adminClient;
    private WebTestClient basicClient;

    @Autowired
    private RestCallerStrategy restCallerStrategy;

    @LocalServerPort
    private String port;

    @BeforeEach
    public void init() {
        this.basicClient = setOptions(BASIC.name());
        this.adminClient = setOptions(ADMIN.name());
    }

    private WebTestClient setOptions(String user) {
        WebTestClient webTestClient = WebTestClient
                .bindToServer().baseUrl("http://localhost:" + port + "/app")
//              .bindToController(new TestController()) -> custom controller
                .filter(basicAuthentication(user, PASSWORD))
                .build();
        webTestClient.options()
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .headers(httpHeaders -> {
                    httpHeaders.setDate(ZonedDateTime.now());
                })
                .cookies(cookie -> cookie.add("cookieTest", "cookieValue"));
        return webTestClient;
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
                .expectBody();
    }

    @Test
    void testAccountGetAllAsAdmin() {
        adminClient.get()
                .uri(BASE_ACCOUNT_URL + "all")

                .body(Mono.just(newRepoDetails), RepoRequest.class)

                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class);

                .jsonPath("$.name").isEqualTo("updated-webclient-repository");
                .returnResult(ResponseEntity.class);
                .consumeWith(response ->
                Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    void testGetAllAsBasic() {
        basicClient
                .get()
                .uri(BASE_ACCOUNT_URL + "all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(PASSWORD))
                .exchange()
                .expectStatus().isForbidden();
    }
}
