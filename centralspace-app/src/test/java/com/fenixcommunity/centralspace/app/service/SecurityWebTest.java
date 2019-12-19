package com.fenixcommunity.centralspace.app.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = com.fenixcommunity.centralspace.app.CentralspaceApplication.class)
public class SecurityWebTest {

    private TestRestTemplate restTemplate;
    private URL baseUrl;

    @LocalServerPort
    private String port;
test
    @BeforeEach
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("admin", "admin");
        baseUrl = new URL("http://localhost:" + port + "/api/logger");
    }

    @Test
    public void whenLoggedUserRequestsHomePage_ThenSuccess()
            throws IllegalStateException{
        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response
                .getBody()
                .contains("Baeldung"));
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage() {

        restTemplate = new TestRestTemplate("user", "wrongpassword");
        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response
                .getBody()
                .contains("Unauthorized"));
    }

}