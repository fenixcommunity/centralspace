package com.fenixcommunity.centralspace.app.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializerApp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("InitializerApp init");
    }
}
