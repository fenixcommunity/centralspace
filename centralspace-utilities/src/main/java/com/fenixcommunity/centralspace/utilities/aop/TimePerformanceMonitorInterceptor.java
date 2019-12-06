package com.fenixcommunity.centralspace.utilities.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import java.time.ZonedDateTime;

public class TimePerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {

    public TimePerformanceMonitorInterceptor() {
    }

    public TimePerformanceMonitorInterceptor(boolean useDynamicLogger) {
        setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log log)
            throws Throwable {
        String name = createInvocationTraceName(invocation);
        long start = System.currentTimeMillis();
        log.info("Method " + name + " execution started at:" + ZonedDateTime.now().toString());
        try {
            return invocation.proceed();
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            log.info("Method " + name + " execution lasted:" + time + " ms");
            log.info("Method " + name + " execution ended at:" + ZonedDateTime.now().toString());

            if (time > 10) {
                log.warn("Method execution longer than 10 ms!");
            }
        }
    }

}
