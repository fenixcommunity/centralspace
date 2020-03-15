package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.utilities.common.OperationLevel.HIGH;
import static com.fenixcommunity.centralspace.utilities.common.Var.ADMIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.COOKIE_SESSION;
import static com.fenixcommunity.centralspace.utilities.common.Var.HEADER_SESSION;
import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static com.fenixcommunity.centralspace.utilities.web.WebTool.removeLinks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fenixcommunity.centralspace.app.rest.dto.account.AccountDto;
import com.fenixcommunity.centralspace.app.rest.filter.HeaderApiFilter;
import com.fenixcommunity.centralspace.app.rest.filter.cache.CacheCookieApiFilter;
import com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        AccountController.class,
        AccountService.class,
        HeaderApiFilter.class, CacheCookieApiFilter.class
})
@WebMvcTest
@WithMockUser(username = ADMIN, roles = {ADMIN}, password = PASSWORD)
// 4 APPROACH https://thepracticaldeveloper.com/2017/07/31/guide-spring-boot-controller-tests/
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @SuppressWarnings("unused")
    private JacksonTester<AccountDto> jacksonTester;

    @MockBean
    private AccountService accountService;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private ZonedDateTime dateTime;

    private static final String BASE_ACCOUNT_URL = "/api/account/";
    private Account account;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        initAccount();
    }

    private void initAccount() {
//            .apply(SecurityMockMvcConfigurers.springSecurity())
        Account account = Account.builder()
                .id(ID)
                .login(LOGIN)
                .mail(MAIL)
                .passwords(Collections.singletonList(null))
                .build();
        this.account = account;
        when(accountService.findById(ID)).thenReturn(Optional.of(account));
    }

    @Test
// we can add also in method level    @WithMockUser(username = ADMIN, roles = {ADMIN}, password = PASSWORD)
    void shouldReturnResponse_ForAccountIdCall() throws Exception {
        var response = mvc.perform(get(BASE_ACCOUNT_URL + ID)
                // when POST .content(jacksonTester.write(account).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();
        String responseContent = removeLinks(response.getContentAsString());
        String accountJson = jacksonTester.write(new AccountMapper().mapToDto(account, HIGH)).getJson();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(removeLinks(accountJson).contains(responseContent));
    }

    @Test
    void shouldReturnOkData_ForAccountIdCall() throws Exception {
        mvc.perform(get(BASE_ACCOUNT_URL + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        ).andDo(print())
                .andExpect(status().is2xxSuccessful())
                //todo .andExpect(jsonPath("$.account").exists())
                //      .andExpect(jsonPath("$.account[*].id").value(ID))
                .andExpect(jsonPath("id").value(ID))
                .andExpect(jsonPath("login").isNotEmpty());
//        .andExpect("links[0]");

    }

    @Test
    void shouldSetHeaderAndCookie_AfterFilterAction() throws Exception {
        var response = mvc.perform(get(BASE_ACCOUNT_URL + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
//              .andExpect(result -> result.getResponse().containsHeader(HEADER_SESSION))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.containsHeader(HEADER_SESSION));
        assertNotNull(response.getCookie(COOKIE_SESSION));
    }

}
