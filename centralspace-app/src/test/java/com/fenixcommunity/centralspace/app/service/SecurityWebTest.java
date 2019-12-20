package com.fenixcommunity.centralspace.app.service;

import com.fenixcommunity.centralspace.app.configuration.CentralspaceApplicationConfig;
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

import static com.fenixcommunity.centralspace.app.configuration.security.SecurityRole.ADMIN;
import static com.fenixcommunity.centralspace.app.configuration.security.SecurityRole.BASIC;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static com.fenixcommunity.centralspace.utilities.common.Var.WRONG_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.util.Assert.hasText;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = CentralspaceApplicationConfig.class)
public class SecurityWebTest {

    private TestRestTemplate restTemplate;
    private URL baseUrl;

    @LocalServerPort
    private String port;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate(ADMIN.name(), PASSWORD);
        baseUrl = new URL("http://localhost:" + port + "/api/logger/run");
    }

    @Test
    public void whenLoggedUserRequestsHomePage_ThenSuccess()
            throws IllegalStateException{
        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage() {

        restTemplate = new TestRestTemplate(BASIC.name(), WRONG_PASSWORD);
        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        hasText(response.getBody(), "Unauthorized");
    }

}