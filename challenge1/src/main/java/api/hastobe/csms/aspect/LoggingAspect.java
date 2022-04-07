package api.hastobe.csms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) ||" +
            "within(@org.springframework.stereotype.Service *) ||" +
            "within(@org.springframework.stereotype.Repository *) ||"+
            "within(@org.springframework.stereotype.Component *)")
    public void beansPointcut() {}

    @Pointcut("within(api.hastobe.csms.rest..*) ||" +
            "within(api.hastobe.csms.service..*)")
    public void packagesPointcut() {}

    @Around("packagesPointcut() && beansPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = getLogger(joinPoint);
        if (logger.isDebugEnabled()) {
            logger.debug("----> Entered {} with {}", joinPoint.getSignature().getName(), Arrays.asList(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (logger.isDebugEnabled()) {
                logger.debug("----> Exiting {} with results: {}", joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (Exception e) {
            logger.error("----> An Error was occurred!");
            throw e;
        }
    }
}
