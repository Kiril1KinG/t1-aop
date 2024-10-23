package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.demo.service..*(..))")
    public void logMethodExecution(JoinPoint joinPoint) {
        log.debug(
                String.format("Method %s start execution with args %s",
                        joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()))
        );
    }

    @AfterReturning(
            pointcut = "execution(* com.example.demo.service..*(..))",
            returning = "value")
    public void logMethodReturnedValue(JoinPoint joinPoint, Object value) {
        if (value == null) {
            log.debug(
                    String.format("Method %s returned null",
                            joinPoint.getSignature().getName())
            );
            return;
        }
        log.debug(
                String.format("Method %s returned value %s",
                        joinPoint.getSignature().getName(), value.toString())
        );
    }

    @Around("execution(public * com.example.demo.service..*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) {
        long millis = System.currentTimeMillis();

        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        log.debug(String.format("Method %s completed execution for %d milliseconds",
                proceedingJoinPoint.getSignature().getName(), System.currentTimeMillis() - millis)
        );

        return result;
    }

    @AfterThrowing(
            pointcut = "execution(* com.example.demo..*(..))",
            throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        log.warn(String.format("Method %s produced exception %s, with cause %s",
                joinPoint.getSignature().getName(), exception, exception.getCause())
        );
    }
}
