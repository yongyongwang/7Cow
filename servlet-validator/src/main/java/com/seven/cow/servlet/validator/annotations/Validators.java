package com.seven.cow.servlet.validator.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validators {
    Validated[] value() default {};
}
