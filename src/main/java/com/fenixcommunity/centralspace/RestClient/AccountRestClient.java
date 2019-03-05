//package com.fenixcommunity.centralspace.RestClient;
//
//import com.fenixcommunity.centralspace.Account.Account;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class AccountRestClient {
//
//    private static final String GET_USERS_ENDPOINT_URL = "http://localhost:8080/accounts";
//    private static final String GET_USER_ENDPOINT_URL = "http://localhost:8080/account/{id}";
//    private static final String CREATE_USER_ENDPOINT_URL = "http://localhost:8080/account";
//    private static final String UPDATE_USER_ENDPOINT_URL = "http://localhost:8080/account/{id}";
//    private static final String DELETE_USER_ENDPOINT_URL = "http://localhost:8080/account/{id}";
//
//    // pobaw sie + HttpHeaders
//    private static RestTemplate restTemplate = new RestTemplate();
//
//    public static void main(String[] args) {
//        AccountRestClient restClient = new AccountRestClient();
//
//        restClient.getAccounts();
//        restClient.getAccountById();
//        restClient.createAccount();
//        restClient.updateAccount();
//        restClient.deleteAccount();
//    }
//
//    private void getAccounts() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//
//        ResponseEntity response = restTemplate.exchange(GET_USERS_ENDPOINT_URL, HttpMethod.GET, entity, String.class);
//        System.out.println(response);
//    }
//
//    private void getAccountById() {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("id", "1");
//
//        Account result = restTemplate.getForObject(GET_USER_ENDPOINT_URL, Account.class, parameters);
//        System.out.println(result);
//    }
//
//    private void createAccount() {
//        Account requestAccount = new Account(3L, "maxTest3");
//
//        Account response = restTemplate.postForObject(CREATE_USER_ENDPOINT_URL, requestAccount, Account.class);
//        System.out.println(response);
//    }
//
//    private void updateAccount() {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("id", "1");
//        Account requestAccount = new Account(1L, "maxTest1Updated");
//
//        restTemplate.put(UPDATE_USER_ENDPOINT_URL, requestAccount, parameters);
//        System.out.println("updateAccount ok");
//    }
//
//    private void deleteAccount() {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("id", "2");
//
//        restTemplate.delete(DELETE_USER_ENDPOINT_URL, parameters);
//    }
//
//}
