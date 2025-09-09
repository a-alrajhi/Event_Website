package com.event_website.Logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class RequestLoggingAspect {

    private static final Logger requestLogger = LoggerFactory.getLogger("RequestLogger");

    // Pointcut to catch all controller methods
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void allControllerMethods() {}

    @Before("allControllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String uri = request.getRequestURI();
        String method = request.getMethod();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method methodObj = signature.getMethod();

        LogRequest logAnnotation = methodObj.getAnnotation(LogRequest.class);

        if (logAnnotation != null) {
            requestLogger.info("🔍 [Annotated] {} {} - {} | Args: {}", method, uri,
                    logAnnotation.description(), Arrays.toString(joinPoint.getArgs()));
        } else {
            requestLogger.info("📥 [Default] {} {}", method, uri);
        }
    }
}
