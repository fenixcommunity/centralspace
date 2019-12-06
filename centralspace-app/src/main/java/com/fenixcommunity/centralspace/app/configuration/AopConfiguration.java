package com.fenixcommunity.centralspace.app.configuration;

import com.fenixcommunity.centralspace.utilities.aop.TimePerformanceMonitorInterceptor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
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
public class AopConfiguration {

//     @Pointcut("within(com.fenixcommunity.centralspace..*)") all package in package and subpackages

//    @Pointcut("@target(org.springframework.stereotype.Repository)")  all repository childs

//     @Pointcut("target(com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate)")
//     @Pointcut("this(com.fenixcommunity.centralspace.utilities.mail.template.RegistrationMailMessage)")

//    @Pointcut("execution(* *..find*(Long,..))")  method ...find and Long and any number of param

//    @Pointcut("firstMethods() && secondMethods()")

    @Pointcut("@annotation(javax.persistence.Converter)")
    public void converterMethods() {}

    @Before("converterMethods()")
    public void logMethod(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        log.info("Converter method run: " + methodName);
    }
test is  https://www.baeldung.com/spring-performance-logging
    String abb = "";
    abb = null;
        assert abb != null;
    //todo time monitor
//    @Pointcut("execution(* com.fenixcommunity.centralspace.app.rest.api.LoggingController.*(..))")
    @Pointcut("execution(public void com.fenixcommunity.centralspace.app.rest.api.LoggingController.run())")
    public void timeMonitor() { }

    @Bean
    public TimePerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new TimePerformanceMonitorInterceptor(true);
    }

    @Bean
    public Advisor myPerformanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(getClassPath() + ".timeMonitor()");
        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
    }

    private String getClassPath() {
        Class thisClass = this.getClass();
        return thisClass.getPackageName() + thisClass.getName();
    }

}
