package com.example.san.Util.Enums;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Aspect
@Configuration
public class ServiceAndUserAspect {

//    private static final Logger logger = LoggerFactory.getLogger("tl");


    @Before("execution(* com.example.san.Service.*.*(..))")         //point-cut expression
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("methoth" + joinPoint.getSignature().getName() + "is started");

    }

    @After("execution(* com.example.san.Service.*.*(..))")         //point-cut expression
    public void logFter(JoinPoint joinPoint) {
        System.out.println("methoth" + joinPoint.getSignature().getName() + "is ende");

    }

    @After("execution(* com.example.san.Service.ISrvProcess.invokeService(..))")         //point-cut expression
    public void afterInvoke(JoinPoint joinPoint) {
        System.out.println("methoth invoke" + joinPoint.getSignature().getName() + "is started");

    }


}
