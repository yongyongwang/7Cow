package com.seven.cow.beans.spring.boot.starter.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 外部服务实现注解bean
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface OuterService {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
