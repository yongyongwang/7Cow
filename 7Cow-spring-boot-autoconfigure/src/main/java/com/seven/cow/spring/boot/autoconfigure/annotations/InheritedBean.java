package com.seven.cow.spring.boot.autoconfigure.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InheritedBean {

}
