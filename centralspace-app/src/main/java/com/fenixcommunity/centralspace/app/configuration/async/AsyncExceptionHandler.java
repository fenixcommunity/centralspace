package com.fenixcommunity.centralspace.app.configuration.async;

import java.lang.reflect.Method;

import lombok.extern.log4j.Log4j2;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

@Log4j2
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(final Throwable throwable, final Method method, final Object... obj) {
        log.debug("Exception message - " + throwable.getMessage());
        log.debug("Method name - " + method.getName());
        for (Object param : obj) {
            log.debug("Parameter value - " + param);
        }
    }
}