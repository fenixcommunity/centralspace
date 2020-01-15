package com.fenixcommunity.centralspace.utilities.aop;

import com.fenixcommunity.centralspace.utilities.time.TimeFormatter;
import com.google.common.base.Stopwatch;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.METHOD_INVOCATION_TIME_LIMIT_mS;
import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TimePerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {

    public TimePerformanceMonitorInterceptor() {
    }

    public TimePerformanceMonitorInterceptor(boolean useDynamicLogger) {
        setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(final MethodInvocation invocation, final Log dynamicLogger)
            throws Throwable {
        final String name = createInvocationTraceName(invocation);
        final Stopwatch watch = Stopwatch.createStarted();
        log.trace("Method " + name + " execution started at: " + LocalDateTime.now().format(TimeFormatter.DT_FORMATTER_2));
        try {
            return invocation.proceed();
        } finally {
            long time = watch.elapsed(TimeUnit.MILLISECONDS);
            log.trace("Method " + name + " execution lasted: " + time + " ms");

            if (time > METHOD_INVOCATION_TIME_LIMIT_mS) {
                log.warn("Method execution longer than ms: " + METHOD_INVOCATION_TIME_LIMIT_mS);
            }
        }
    }

}
