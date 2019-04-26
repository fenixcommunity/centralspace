package com.fenixcommunity.centralspace.app.utils;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FinalizerApp implements ExitCodeGenerator {
    private static final int EXIT_CODE = 42;
    private static final int EXCEPTION_CODE = 1;
    private static final int NUMBER_FORMAT_EXCEPTION_CODE = 80;

    // 1 approach
//    @Bean
//    CommandLineRunner createException() {
//        return args -> Integer.parseInt("test");
//    }

    @Bean
    ExitCodeExceptionMapper exitCodeToExceptionMapper() {
        return exception -> {
            // set exit code base on the exception type
            if (exception.getCause() instanceof NumberFormatException) {
                return NUMBER_FORMAT_EXCEPTION_CODE;
            }
            return EXCEPTION_CODE;
        };
    }

    // 2 approach
    @Bean
    ExitListener demoListenerBean() {
        return new ExitListener();
    }

    private static class ExitListener {
        @EventListener
        public void exitEvent(ExitCodeEvent event) {
            System.out.println("Exit code: " + event.getExitCode());
        }
    }

    // 3 approach + interface implements
    public static void main(String[] args) {
        System.exit(SpringApplication
                .exit(SpringApplication.run(FinalizerApp.class, args)));
    }

    @Override
    public int getExitCode() {
        return EXIT_CODE;
    }


}