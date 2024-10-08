package org.example.shopping.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class CommonAspect {

    private static final Logger logger = LoggerFactory.getLogger(CommonAspect.class);

    @Around("execution(* org.example.shopping.mapper..*(..))")
    public Object logMybatisQueries(ProceedingJoinPoint joinPoint) throws Throwable {
        String metgodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Executing: " + metgodName + " with args: " + Arrays.toString(args));

        Object result = joinPoint.proceed();

        logger.info("Result: " + result);

        return result;
    }

    @Around("execution(* org.example.shopping.controller..*(..)) || execution(* org.example.shopping.service..*(..))")
    public Object logControllerInit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean isController = proceedingJoinPoint.getSignature().getDeclaringTypeName().contains("controller");
        String logSpace = isController ? "" : "  ";

        if (logger.isWarnEnabled()) {
            String name = proceedingJoinPoint.getSignature().getName();

            String[] pathStr = proceedingJoinPoint.getSignature().getDeclaringTypeName().split("\\.");

            StringBuilder splPath = new StringBuilder();

            if (pathStr.length > 3) {
                for (int i = 3; i < pathStr.length; i++) {
                    splPath.append(pathStr[i]);
                    if (i < pathStr.length -1) {
                        splPath.append(".");
                    }
                }
            }

            logger.warn("{} {} [{}] : {} {}",
                    isController ? "--" : "  ┕",
                    isController ? "Controller" : "Service",
                    splPath, name,
                    isController ? "-------------------------------------------" : "");
        }

        Object result = proceedingJoinPoint.proceed();

        if (logger.isWarnEnabled()) {
            logger.warn("{} {}RESULT : {}", logSpace,
                    isController ? "┕ C => " : "┕ S => ", result);
        }

        return result;
    }
}
