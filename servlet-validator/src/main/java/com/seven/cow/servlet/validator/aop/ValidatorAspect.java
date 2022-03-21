package com.seven.cow.servlet.validator.aop;

import com.seven.cow.servlet.validator.exception.ValidationException;
import com.seven.cow.servlet.validator.properties.Expression;
import com.seven.cow.servlet.validator.properties.ValidatorProperties;
import com.seven.cow.servlet.validator.util.ValidatorUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Order(-1000)
public class ValidatorAspect {

    @Resource
    private ValidatorProperties validatorProperties;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) ")
    public void getMappingPoint() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) ")
    public void postMappingPoint() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) ")
    public void requestMappingPoint() {
    }

    @Around("getMappingPoint() || postMappingPoint() || requestMappingPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        List<Expression> expressions = validatorProperties.getRules().get(0).getAssertExpressions();
        for (Expression expression : expressions) {
            if (!ValidatorUtils.valid(expression.getExpression(), method, args, Boolean.class)) {
                throw new ValidationException(expression.getMessage());
            }
        }
        return point.proceed();
    }

}
