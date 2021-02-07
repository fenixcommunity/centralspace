package com.fenixcommunity.centralspace.app.service.security;

import static com.fenixcommunity.centralspace.app.configuration.security.SecurityUserGroup.SWAGGER_USER;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD_HIGH;
import static com.fenixcommunity.centralspace.utilities.common.Var.WRONG_PASSWORD;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.DEFAULT;
import static org.springframework.util.Assert.hasText;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import com.fenixcommunity.centralspace.app.configuration.CentralspaceApplicationConfig;
import com.fenixcommunity.centralspace.app.rest.dto.security.RequestedUserDto;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.clients.jedis.Jedis;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,
        classes = {CentralspaceApplicationConfig.class})
@SqlGroup({
        @Sql(scripts = {"classpath:/script/schema_integration_test.sql"},
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(encoding = "utf-8", transactionMode = DEFAULT)
        )
})
@FieldDefaults(level = PRIVATE)
class SecurityWebIntegrationTest {
    static final String ADMIN_TEST = "ADMIN_TEST";
    static final String BASIC_USER_TEST = "BASIC_USER_TEST";

    private TestRestTemplate restTemplate;
    private URL baseUrl;
    private Jedis jedis;

    @LocalServerPort
    private String port;

    @BeforeEach
    void setUp() {
        jedis = new Jedis("localhost", 6379);
        jedis.flushAll();
    }

    @Test
//    @Ignore  //todo waiting for new swagger release, problem with 3.0.0 version
    void whenUserRequestsLoginPageWithAuthorization_ThenThrowException()
            throws IllegalStateException, MalformedURLException {
        restTemplate = new TestRestTemplate();
        baseUrl = new URL("http://localhost:" + port + "/app/public/users/login-call");
        RequestedUserDto requestedUserDto = RequestedUserDto.builder().username("user").password("pass").role("role").build();
        ResponseEntity<String> response
                = restTemplate.postForEntity(baseUrl.toString(), requestedUserDto, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Actually logged"));
    }

    @Test
    void whenSwaggerUserRequestsSwaggerPage_ThenSuccess()
            throws IllegalStateException, MalformedURLException {
        restTemplate = new TestRestTemplate(SWAGGER_USER.name(), PASSWORD);
        baseUrl = new URL("http://localhost:" + port + "/app/swagger-ui.html");

        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    void whenAdminUserRequestsApiPage_ThenSuccess()
            throws IllegalStateException, MalformedURLException {
        restTemplate = new TestRestTemplate(ADMIN_TEST, PASSWORD_HIGH);
        baseUrl = new URL("http://localhost:" + port + "/app/api/account/all");

        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        Set<String> jedisKeysWithSession = jedis.keys("*");
        assertThat(jedisKeysWithSession).isNotEmpty();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenUserWithWrongRight_thenUnauthorizedStatus() throws MalformedURLException {
        restTemplate = new TestRestTemplate(BASIC_USER_TEST, PASSWORD_HIGH);
        baseUrl = new URL("http://localhost:" + port + "/app/api/account/all");

        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        hasText(response.getBody(), "Forbidden");
    }

    @Test
    void whenUserWithWrongCredentials_thenUnauthorizedPage() throws MalformedURLException {
        restTemplate = new TestRestTemplate(BASIC_USER_TEST, WRONG_PASSWORD);
        baseUrl = new URL("http://localhost:" + port + "/app/api/account/all");

        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl.toString(), String.class);

        assertEquals(HttpStatus.FOUND, response.getStatusCode()); // redirect to login
    }

}