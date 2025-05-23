package org.example.shopping.util.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;


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

    // mapper 는 로그로 별도로 찍어주지 않기 위해 제외.
    @Pointcut("execution(* org.example.shopping.*Mapper.*(..)) || execution(* org.example.shopping.util..*(..))")
    public void excludeMappers() {}

    // controller/service AOP
    @Around("execution(* org.example.shopping.*.controller.*Controller.*(..)) && !excludeMappers()")
    public Object logControllerInit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        boolean isController = proceedingJoinPoint.getSignature().getDeclaringTypeName().contains("Controller");
        String logSpace = isController ? "" : "  ";

        if (log.isWarnEnabled()) {
            String name = proceedingJoinPoint.getSignature().getName();
            String[] pathStr = proceedingJoinPoint.getSignature().getDeclaringTypeName().split("\\.");

            String splPath = Arrays.stream(pathStr)
                    .skip(3)        // 3번째 인덱스부터 시작.
                    .filter(i -> Arrays.asList(pathStr).indexOf(i) % 2 > 0)     // 홀수 인덱스만 필터링
                    .reduce((first, second) -> first + "." + second)        // 문자열 결합.
                    .orElse("");

            log.warn("{} {} [{}] : {} {}",
                    isController ? "--" : "  ┕",
                    isController ? "Controller" : "Service",
                    splPath, name,
                    isController ? "-------------------------------------------" : "");
        }

        Object result = proceedingJoinPoint.proceed();

        if (log.isWarnEnabled() && result != null) {
            log.warn("{} {}RESULT : {}", logSpace,
                    isController ? "┕ C => " : "┕ S => ", result);
        }

        return result;

            /*String name = proceedingJoinPoint.getSignature().getName();

            String[] pathStr = proceedingJoinPoint.getSignature().getDeclaringTypeName().split("\\.");

            StringBuilder splPath = new StringBuilder();

            if (pathStr.length > 3) {
                for (int i = 3; i < pathStr.length; i++) {
                    if (i % 2 > 0) {
                        splPath.append(pathStr[i]);
                        if (i < pathStr.length - 1) {
                            splPath.append(".");
                        }
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

        if (log.isWarnEnabled() && result != null) {
            log.warn("{} {}RESULT : {}", logSpace,
                    isController ? "┕ C => " : "┕ S => ", result);
        }

        return result;*/
    }

    @Around("execution(* org.example.shopping.util.wrapper..*(..)))")
    public Object logWrapperInit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object result = proceedingJoinPoint.proceed();

        if (log.isWarnEnabled() && !(result instanceof Boolean && (Boolean) result)) {
            log.warn("==== RETURN RESULT : {}", result);
        }

        return result;
    }
}
