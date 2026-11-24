package hu.pte.hotelbooking.aspect;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static Logger logger = Logger.getLogger("hu.pte.hotelbooking.aspect");

    // every method, in every class of the service package, any return type, any parameters
    @Pointcut("execution(* hu.pte.hotelbooking.service.*.*(..))")
    public void serviceMethods() {
    }

    // @Around: log before the method, run it with proceed(), log after with the time
    @Around("serviceMethods()")
    public Object logServiceCall(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        logger.log(Level.INFO, "--> " + className + "." + methodName
                + " called with " + Arrays.toString(pjp.getArgs()));

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time = System.currentTimeMillis() - start;

        logger.log(Level.INFO, "<-- " + className + "." + methodName + " finished in " + time + " ms");
        return result;
    }

    // @Before: every method starting with "add" in every class of every package
    @Before("execution(* hu.pte.hotelbooking.*.*.add*(..))")
    public void logBeforeAdd(JoinPoint jp) {
        logger.log(Level.INFO, "add method is being called: " + jp.getSignature().getName());
    }

    // @After: every method of every class whose name ends with "Controller"
    @After("execution(* hu.pte.hotelbooking.*.*Controller.*(..))")
    public void logAfterController(JoinPoint jp) {
        logger.log(Level.INFO, "the method " + jp.getSignature().getName() + " was executed from a controller class!");
    }

    // @AfterThrowing: runs only when a service method throws an exception
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logException(JoinPoint jp, Exception ex) {
        logger.log(Level.WARNING, "!!! " + jp.getSignature().getName() + " threw "
                + ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }
}
