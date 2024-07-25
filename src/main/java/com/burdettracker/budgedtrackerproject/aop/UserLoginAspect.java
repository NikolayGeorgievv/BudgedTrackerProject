package com.burdettracker.budgedtrackerproject.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserLoginAspect {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginAspect.class);

    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))")
    public void userLogin() {
    }

    @AfterReturning(pointcut = "userLogin()", returning = "result")
    public void logUserLogin(JoinPoint joinPoint, Object result) {
        Authentication auth = (Authentication) result;
        logger.info("User {} logged in", auth.getName());
    }
}