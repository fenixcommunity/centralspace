package com.fenixcommunity.centralspace.app.configuration.aop;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.getClassPath;
import static com.fenixcommunity.centralspace.utilities.common.Var.OR;
import static lombok.AccessLevel.PRIVATE;

import java.util.Arrays;

import com.fenixcommunity.centralspace.utilities.aop.TimePerformanceMonitorInterceptor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Aspect
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AopConfg {

    @Bean
    TimePerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new TimePerformanceMonitorInterceptor(false);
    }

    @Bean
    Advisor appPerformanceMonitorAdvisor() {
        final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(getClassPath(this.getClass()) + ".timeMonitor()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
    }

    //package logging
    @Pointcut("execution(* com.fenixcommunity.centralspace.domain.repository..*.*(..))")
    public void monitorRepository() {
    }

    @Before("monitorRepository()")
    public void logJoinPoint(final JoinPoint joinPoint) {
        logJoinPointBefore(joinPoint);
    }

    @AfterReturning(pointcut = "monitorRepository()", returning = "result")
    public void logAfter(final JoinPoint joinPoint, final Object result) {
        logJoinPointAfter(joinPoint, result);
    }

    @AfterThrowing(pointcut = "monitorRepository()", throwing = "ex")
    public void logAfterThrowing(final JoinPoint joinPoint, final Throwable ex) {
        logJoinPointAfterThrowing(joinPoint, ex);
    }

    //2019-12-30 13:56:02,881 main ERROR Unable to locate appender "RollingFile" for logger config "org.mabb.fontverter"
    //time logging
    @Around("@annotation(com.fenixcommunity.centralspace.app.configuration.annotation.MethodMonitoring)")
    public void logMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        logJoinPointBefore(joinPoint);
        joinPoint.proceed();
        logJoinPointAfter(joinPoint, null);
    }

    @Pointcut("execution(public String com.fenixcommunity.centralspace.app.rest.api.LoggingController.*(..)) "
            + OR + " execution(public * com.fenixcommunity.centralspace.app.service.AccountService.findById(..)) "
            + OR + " execution(public * com.fenixcommunity.centralspace.app.service.document.DocumentService.*(..))")
    @SuppressWarnings("unused")
    public void timeMonitor() {
    }

    @Before("timeMonitor()")
    public void logMethod(final JoinPoint joinPoint) {
        logJoinPointBefore(joinPoint);
    }


    private void logJoinPointBefore(final JoinPoint joinPoint) {
        log.trace("Signature declaring type : " + joinPoint.getSignature().getDeclaringTypeName());
        log.trace("Signature name : " + joinPoint.getSignature().getName());
        log.trace("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        log.trace("Target class : " + joinPoint.getTarget().getClass().getName());
    }

    private void logJoinPointAfter(final JoinPoint joinPoint, final Object result) {
        log.trace("Exiting from Method :" + joinPoint.getSignature().getName());
        if (result != null) {
            log.trace("Return value :" + result);
        } else {
            log.trace("No return value");
        }
    }

    private void logJoinPointAfterThrowing(final JoinPoint joinPoint, final Throwable ex) {
        log.trace("An exception has been thrown in " + joinPoint.getSignature().getName() + "()");
        log.trace("Cause :" + ex.getCause());
    }

//     @Pointcut("within(com.fenixcommunity.centralspace..*)") all package in package and subpackages

//    @Pointcut("@target(org.springframework.stereotype.Repository)")  all repository childs

//     @Pointcut("target(com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate)")
//     @Pointcut("this(com.fenixcommunity.centralspace.utilities.mail.template.RegistrationMailMessage)")

//    @Pointcut("execution(* *..find*(Long,..))")  method ...find and Long and any number of param

//    @Pointcut("firstMethods() && secondMethods()")

}
