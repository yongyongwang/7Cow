package com.seven.cow.servlet.cache.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {

    @AliasFor("cacheName")
    String value() default "";

    @AliasFor("value")
    String cacheName() default "";

    String key() default "";

    String condition() default "";

    String unless() default "";

    TimeUnit expireUnit() default TimeUnit.HOURS;

    long expireTime() default 2;

}
