package com.seven.cow.servlet.logging.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seven.cow.spring.boot.autoconfigure.annotations.InheritedBean;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Order(-2)
@InheritedBean
public class RequestAspect {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "&& !@annotation(IgnoreLogging)")
    public void getMappingPoint() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "&& !@annotation(IgnoreLogging)")
    public void postMappingPoint() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "&& !execution(* org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.*(..)) " +
            "&& !@annotation(IgnoreLogging)")
    public void requestMappingPoint() {
    }

    @Around("getMappingPoint() || postMappingPoint() || requestMappingPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LoggerUtils.info("------ > Rest Input: " + ((null == point.getArgs()) ? "null" : Arrays.stream(point.getArgs())
                .filter(o -> !(o instanceof HttpServletRequest || o instanceof HttpServletResponse
                        || o instanceof MultipartFile
                        || o instanceof MultipartFile[]))
                .map(o -> {
                    try {
                        return objectMapper.writeValueAsString(o);
                    } catch (JsonProcessingException e) {
                        LoggerUtils.error("rest controller cast to json exception:", e);
                    }
                    return "";
                }).collect(Collectors.joining(" | "))));
        Object result = point.proceed();
        stopWatch.stop();
        LoggerUtils.info("< ------ Rest Output: " + ((null == result) ? "null" : objectMapper.writeValueAsString(result)) + "   method cost: " + stopWatch.getTotalTimeMillis());
        return result;
    }

}
