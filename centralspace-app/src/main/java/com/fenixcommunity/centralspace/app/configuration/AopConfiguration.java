package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.utilities.aop.TimePerformanceMonitorInterceptor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
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

import static com.fenixcommunity.centralspace.utilities.common.DevTool.getClassPath;

@Configuration
@EnableAspectJAutoProxy
@Aspect
@Log4j2
public class AopConfiguration {

//     @Pointcut("within(com.fenixcommunity.centralspace..*)") all package in package and subpackages

//    @Pointcut("@target(org.springframework.stereotype.Repository)")  all repository childs

//     @Pointcut("target(com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate)")
//     @Pointcut("this(com.fenixcommunity.centralspace.utilities.mail.template.RegistrationMailMessage)")

//    @Pointcut("execution(* *..find*(Long,..))")  method ...find and Long and any number of param

//    @Pointcut("firstMethods() && secondMethods()")


    @Around("@annotation(com.fenixcommunity.centralspace.utilities.aop.AppMonitoring)")
    public void logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature methodSignature = joinPoint.getSignature();
        log.trace("AppMonitoring START: " + methodSignature);
        joinPoint.proceed();
        log.trace("AppMonitoring END");
    }

//    @Pointcut("execution(* com.fenixcommunity.centralspace.app.rest.api.LoggingController.*(..))")
    @Pointcut("execution(public String com.fenixcommunity.centralspace.app.rest.api.LoggingController.run())")
    @SuppressWarnings("unused")
    public void timeMonitor() { }

    @Before("timeMonitor()")
    public void logMethod(JoinPoint joinPoint) {
        Signature methodSignature = joinPoint.getSignature();
        StringBuilder text = new StringBuilder("TimeMonitor RUN: ");
        text.append(joinPoint.getSignature().getName());
        text.append("\n args: ");
        text.append(joinPoint.getArgs().toString());
        log.trace(text.toString());
    }

    @Bean
    public TimePerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new TimePerformanceMonitorInterceptor(false);
    }

    @Bean
    public Advisor myPerformanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(getClassPath(this.getClass()) + ".timeMonitor()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
    }


}
