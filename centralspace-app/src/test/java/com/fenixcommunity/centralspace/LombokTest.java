//package com.fenixcommunity.centralspace;
//
//import com.fenixcommunity.centralspace.utils.LombokExample;
//import com.fenixcommunity.centralspace.Password.Password;
//import com.fenixcommunity.centralspace.Account.Account;
//import lombok.Lombok;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.Console;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//public class LombokTest {
//
//    private final Account accountInit;
//    private final String LOGIN_INIT = "loginInit";
//    private Account accountToCompare;
//    private final AtomicLong counter = new AtomicLong();
//
//    {
//        accountInit = createAccount(LOGIN_INIT);
//    }
//
//    private Account createAccount(String login) {
//        Password password = Password.builder().build();
//        return new Account(counter.incrementAndGet(), login, password);
//    }
//
//    private void show(String log) {
//        System.out.println(log);
//    }
//
//    @Before
//    public void init() {
//    }
//
//    @Test
//    public void toStringTest() {
//        String toString = accountInit.toString();
//        Assert.assertTrue(toString.contains(LOGIN_INIT));
//    }
//
//    @Test
//    public void toEqualsAndGetterSetterTest() {
//        accountToCompare = new Account(accountInit.getId(),accountInit.getLogin(),accountInit.getPassword());
//        Assert.assertEquals(accountToCompare,accountInit);
//        accountToCompare.setLogin("otherLogin");
//        Assert.assertNotEquals(accountToCompare, accountInit);
//    }
//
//    // majac obiekt mozemy jeszcze dostac sie do wartosci pol prywatnych
//    @Test
//    public void logTest() throws IllegalAccessException {
//        boolean result = false;
//        LombokExample lombokExample = new LombokExample();
//        Field[] fields = lombokExample.getClass().getDeclaredFields();
//        List <Field> modifieredPrivateFields = Arrays.stream(fields)
//                .filter(f -> Modifier.isPrivate(f.getModifiers()))
//                .peek(f -> f.setAccessible(true)).collect(Collectors.toList());
//
//        for (Field field: modifieredPrivateFields) {
//            if (field.get(lombokExample) instanceof Logger) {
//                result = true;
//            }
//        }
//
//        Assert.assertTrue(result);
//    }
//
//}
