package org.example.shopping.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j      // 아래 주석된 final Logger 라인의 어노테이션 버전.
public class CommonAspect {

//    private static final Logger logger = LoggerFactory.getLogger(CommonAspect.class);     // 이렇게 사용해도 되긴함.

    // 매퍼 aop, controller/service AOP와 내용이 겹치므로 주석처리.
//    @Around("execution(* org.example.shopping.mapper..*(..))")
//    public Object logMybatisQueries(ProceedingJoinPoint joinPoint) throws Throwable {
//        String metgodName = joinPoint.getSignature().toShortString();
//        Object[] args = joinPoint.getArgs();
//
//        logger.info("Executing: " + metgodName + " with args: " + Arrays.toString(args));
//
//        Object result = joinPoint.proceed();
//
//        logger.info("Result: " + result);
//
//        return result;
//    }

    // controller/service AOP
    @Around("execution(* org.example.shopping.controller..*(..)) || execution(* org.example.shopping.service..*(..))")
    public Object logControllerInit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        boolean isController = proceedingJoinPoint.getSignature().getDeclaringTypeName().contains("controller");
        String logSpace = isController ? "" : "  ";

        if (log.isWarnEnabled()) {
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

            log.warn("{} {} [{}] : {} {}",
                    isController ? "--" : "  ┕",
                    isController ? "Controller" : "Service",
                    splPath, name,
                    isController ? "-------------------------------------------" : "");
        }

        Object result = proceedingJoinPoint.proceed();

        if (log.isWarnEnabled()) {
            log.warn("{} {}RESULT : {}", logSpace,
                    isController ? "┕ C => " : "┕ S => ", result);
        }

        return result;
    }
}
