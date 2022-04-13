package com.seven.cow.servlet.cache.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheEvict {

    @AliasFor("cacheName")
    String value() default "";

    @AliasFor("value")
    String cacheName() default "";

    String key() default "";

    String condition() default "";

    String unless() default "";

}
