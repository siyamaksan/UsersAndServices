package com.example.san.enums;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class ServiceAndUserAspect {

//    private static final Logger logger = LoggerFactory.getLogger("tl");


  @Before("execution(* com.example.san.service.*.*(..))")         //point-cut expression
  public void logBefore(JoinPoint joinPoint) {
    log.info("method {} is started", joinPoint.getSignature().getName());

  }

  @After("execution(* com.example.san.service.*.*(..))")         //point-cut expression
  public void logAfter(JoinPoint joinPoint) {
    log.info("method {} is ended", joinPoint.getSignature().getName());

  }

  @After("execution(* com.example.san.service.ISrvProcess.invokeService(..))")
  //point-cut expression
  public void afterInvoke(JoinPoint joinPoint) {
    log.info("method invoke {} is started", joinPoint.getSignature().getName());
  }


}
