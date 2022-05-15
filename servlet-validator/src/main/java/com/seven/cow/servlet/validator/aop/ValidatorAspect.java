package com.seven.cow.servlet.validator.aop;

import com.seven.cow.servlet.validator.annotations.Validated;
import com.seven.cow.servlet.validator.annotations.Validators;
import com.seven.cow.servlet.validator.exception.ValidationException;
import com.seven.cow.servlet.validator.util.ValidatorUtils;
import com.seven.cow.spring.boot.autoconfigure.annotations.InheritedBean;
import com.seven.cow.spring.boot.autoconfigure.entity.BaseError;
import com.seven.cow.spring.boot.autoconfigure.entity.Error;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

@Aspect
@Order(-1000)
@InheritedBean
public class ValidatorAspect {

    @Pointcut("@annotation(com.seven.cow.servlet.validator.annotations.Validators) ")
    public void getValidatorPoint() {
    }

    @Around("getValidatorPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Validators validators = method.getAnnotation(Validators.class);
        if (null != validators) {
            Validated[] validates = validators.value();
            for (Validated validated : validates) {
                try {
                    Boolean flag = ValidatorUtils.valid(validated.expression(), method, args, Boolean.class);
                    if (Boolean.FALSE.equals(flag)) {
                        throw new ValidationException(() -> new Error() {
                            @Override
                            public String getErrorCode() {
                                return validated.code();
                            }

                            @Override
                            public String getErrorMsg() {
                                return validated.message();
                            }
                        });
                    }
                } catch (Exception ex) {
                    if (ex instanceof ValidationException) {
                        throw ex;
                    }
                    throw new ValidationException(validated.message(), ex);
                }
            }
        }
        return point.proceed();
    }

}
