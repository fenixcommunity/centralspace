package com.fenixcommunity.centralspace.app.service.security;

import com.fenixcommunity.centralspace.app.configuration.CentralspaceApplicationConfig;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.ADMIN;
import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.app.configuration.security.autosecurity.SecurityRole.SWAGGER;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static com.fenixcommunity.centralspace.utilities.common.Var.WRONG_PASSWORD;
import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.util.Assert.hasText;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,
        classes = {CentralspaceApplicationConfig.class})
@FieldDefaults(level = PRIVATE)
        //todo to other, to avoid variables withot access level
class SecurityWebTest {

    private TestRestTemplate restTemplate;
    private URL baseUrl;

    @LocalServerPort
    private String port;

    @BeforeEach
    void setUp() {
    }


    @Test
//    @Ignore  //todo waiting for new swagger release, problem with 3.0.0 version
    void whenUserRequestsLoginPageWithoutAuthorisation_ThenSuccess()
            throws IllegalStateException, MalformedURLException {
        restTemplate = new TestRestTemplate();
        baseUrl = new URL("http://localhost:" + port + "/app/public/users");
        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenBasicUserRequestsSwaggerPage_ThenSuccess()
            throws IllegalStateException, MalformedURLException {
        restTemplate = new TestRestTemplate(BASIC.name(), PASSWORD);
        baseUrl = new URL("http://localhost:" + port + "/app/swagger-ui.html");

        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenAdminUserRequestsApiPage_ThenSuccess()
            throws IllegalStateException, MalformedURLException {
        restTemplate = new TestRestTemplate(ADMIN.name(), PASSWORD);
        baseUrl = new URL("http://localhost:" + port + "/app/api/account/all");

        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenUserWithWrongRight_thenUnauthorizedStatus() throws MalformedURLException {
        restTemplate = new TestRestTemplate(SWAGGER.name(), PASSWORD);
        baseUrl = new URL("http://localhost:" + port + "/app/api/account/all");

        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        hasText(response.getBody(), "Forbidden");
    }

    @Test
    void whenUserWithWrongCredentials_thenUnauthorizedPage() throws MalformedURLException {
        restTemplate = new TestRestTemplate(BASIC.name(), WRONG_PASSWORD);
        baseUrl = new URL("http://localhost:" + port + "/app/api/account/all");

        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);
        String responseBody = response.getBody();

        hasText(responseBody, "responseBody should be not empty");
        assertTrue(response.getBody().contains("Please sign in")
                && response.getBody().contains("<!DOCTYPE html>"));
    }

}