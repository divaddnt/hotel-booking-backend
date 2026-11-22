package hu.pte.hotelbooking.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Every public method of every class in the service package
    @Pointcut("execution(* hu.pte.hotelbooking.service.*.*(..))")
    public void serviceMethods() {
    }

    // Runs around the method: log before, run it, log after with the time
    @Around("serviceMethods()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        log.info("--> {} called with {}", method, Arrays.toString(joinPoint.getArgs()));

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - start;

        log.info("<-- {} finished in {} ms", method, time);
        return result;
    }

    // Runs only when the method throws an exception
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.warn("!!! {} threw {}: {}", joinPoint.getSignature().toShortString(),
                ex.getClass().getSimpleName(), ex.getMessage());
    }
}
