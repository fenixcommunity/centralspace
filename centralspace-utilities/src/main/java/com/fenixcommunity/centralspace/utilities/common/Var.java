package com.fenixcommunity.centralspace.utilities.common;

import static lombok.AccessLevel.PUBLIC;
import java.security.SecureRandom;

import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;

@FieldDefaults(level = PUBLIC, makeFinal = true)
public class Var {
    public static final SecureRandom secureRandom = new SecureRandom();

    public static final Long ID = 1L;
    public static final Long WRONG_ID = 999999L;
    public static final String NAME = "name";
    public static final String TEXT = "text";
    public static final String EMPTY = "empty";

    public static final String LOGIN = "login123";
    public static final String LOGIN_UPPER = "LOGIN123";
    public static final String PASSWORD_HIGH = "Password1&23";
    public static final String PASSWORD_LOW = "password1";
    public static final String PASSWORD = "password";
    public static final String WRONG_PASSWORD = "wrongpassword";
    public static final String PESEL = "91201045021";
    public static final String NIP = "682-114-39-79";
    public static final String USER = "user";
    public static final String ADMIN = "admin";

    public static final String HEADER_SESSION = "CENTRALSPACE-APP-HEADER-SESSION";
    public static final String COOKIE_SESSION = "CENTRALSPACE-APP-COOKIE-SESSION";
    public static final String DOMAIN_ID = "K343kdndnwok&kerfj02kJNNodok3Zfj87439Kf83";
    public static final String DOMAIN_URL = "www.fenixcommunity.pl";

    public static final String DOT = ".";
    public static final String UNDERSCORE = "_";
    public static final String SLASH = "/";
    public static final String LINE = "-";
    public static final String NEW_LINE = "\n";
    public static final String SEPARATOR = "\n|~~~~~~~~~~|\n";
    public static final String SPACE = " ";
    public static final String OR = "||";
    public static final String UTF_8 = "UTF-8";
    public static final String NUMBER_WATERMARK = "%d";
    public static final String IMAGE = "IMAGE";
    //MAIL
    public static final String EMAIL_FROM = "hermes.delivery.noreply@gmail.com";
    public static final String EMAIL_REPLY_TO = "hermes.delivery.noreply@gmail.com";
    public static final String EMAIL = "max3112@o2.com";
    public static final String SUBJECT = "Message subject";
    public static final String MESSAGE = "Massage to you, your welcome";
    public static final String BASIC_MAIL = "Basic Mail";
    public static final String REGISTRATION_MAIL = "Registration Mail";
    public static final String MAIL = "text@gmail.com";
    public static final String COUNTRY = "Poland";
    public static final String CITY = "Cracow";

    public static long anyLong() {
        return secureRandom.nextLong();
    }

    public static String anyString() {
        return RandomStringUtils.random(10);
    }

    public static String anyString(int count) {
        return RandomStringUtils.random(count);
    }

}