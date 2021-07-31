package com.example.san.Util.Enums;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ServiceAndUserAspect {


    @Around("execution(* com.example.san.Service.*(..))")         //point-cut expression
    public void log(ProceedingJoinPoint joinPoint)
    {
        System.out.println("EmployeeCRUDAspect.logBefore() : " + joinPoint.getSignature().getName());
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("EmployeeCRUDAspect.logAfter() : " + joinPoint.getSignature().getName());
    }
}
