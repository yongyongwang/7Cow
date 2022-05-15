package com.seven.cow.servlet.validator.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Validated {

    String expression();

    String message();

    String code() default "";

}
