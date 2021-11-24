package com.seven.cow.servlet.logging.aop;

import com.alibaba.fastjson.JSON;
import com.seven.cow.spring.boot.autoconfigure.util.CurrentContext;
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

import static com.seven.cow.spring.boot.autoconfigure.constant.Conts.X_CURRENT_REQUEST_REST_INPUT;
import static com.seven.cow.spring.boot.autoconfigure.constant.Conts.X_CURRENT_REQUEST_REST_OUTPUT;

/**
 * @ClassName RequestAspect
 * @Description 打印出入参
 * Version 0.0.1
 **/
@Aspect
@Order(-2)
public class RequestAspect {

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
        CurrentContext.set(X_CURRENT_REQUEST_REST_INPUT, "------ > Rest Input: " + ((null == point.getArgs()) ? "null" : Arrays.stream(point.getArgs())
                .filter(o -> !(o instanceof HttpServletRequest || o instanceof HttpServletResponse
                        || o instanceof MultipartFile))
                .map(JSON::toJSONString).collect(Collectors.joining(" | "))));
        Object result = point.proceed();
        stopWatch.stop();
        CurrentContext.set(X_CURRENT_REQUEST_REST_OUTPUT, "< ------ Rest Output: " + ((null == result) ? "null" : JSON.toJSONString(result)) + "   method cost: " + stopWatch.getTotalTimeMillis());
        return result;
    }

}
