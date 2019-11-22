package com.fenixcommunity.centralspace.app.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.filter.HeaderApiFilter;
import com.fenixcommunity.centralspace.app.rest.filter.cache.CacheCookieApiFilter;
import com.fenixcommunity.centralspace.app.service.AccountService;
import com.fenixcommunity.centralspace.domain.model.account.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper.mapToDto;
import static com.fenixcommunity.centralspace.utilities.common.Level.HIGH;
import static com.fenixcommunity.centralspace.utilities.rest.RestTool.removeLinks;
import static com.fenixcommunity.centralspace.utills.common.test.Var.COOKIE_SESSION;
import static com.fenixcommunity.centralspace.utills.common.test.Var.EMAIL;
import static com.fenixcommunity.centralspace.utills.common.test.Var.HEADER_SESSION;
import static com.fenixcommunity.centralspace.utills.common.test.Var.ID;
import static com.fenixcommunity.centralspace.utills.common.test.Var.LOGIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        AccountController.class,
        AccountService.class,
        HeaderApiFilter.class, CacheCookieApiFilter.class
})
@WebMvcTest
// 4 APPROACH https://thepracticaldeveloper.com/2017/07/31/guide-spring-boot-controller-tests/
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @SuppressWarnings("unused")
    private JacksonTester<AccountDto> jacksonTester;

    @MockBean
    private AccountService accountService;

    private static final String BASE_ACCOUNT_URL = "/account/";
    private static final String BASE_PASSWORD_URL = "/password/";
    private Account account;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        initAccount();
    }

    private void initAccount() {
//            .apply(SecurityMockMvcConfigurers.springSecurity())
        account = Account.builder()
                .id(ID)
                .login(LOGIN)
                .email(EMAIL)
                .passwords(Collections.singletonList(null))
                .build();
        when(accountService.findById(ID)).thenReturn(Optional.of(account));
    }

    @Test
    public void shouldReturnResponse_ForAccountIdCall() throws Exception {
        var response = mvc.perform(get(BASE_ACCOUNT_URL + ID)
                // when POST .content(jacksonTester.write(account).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();
        String responseContent = removeLinks(response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(jacksonTester.write(mapToDto(account, HIGH)).getJson().contains(responseContent));
    }

    @Test
    public void shouldReturnOkData_ForAccountIdCall() throws Exception {
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
    public void shouldSetHeaderAndCookie_AfterFilterAction() throws Exception {
        var response = mvc.perform(get(BASE_ACCOUNT_URL + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
//              .andExpect(result -> result.getResponse().containsHeader(HEADER_SESSION))
                .andReturn().getResponse();

        assertTrue(response.containsHeader(HEADER_SESSION));
        assertNotNull(response.getCookie(COOKIE_SESSION));
    }

}
