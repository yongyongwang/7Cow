package com.seven.cow.beans.spring.boot.starter.annotations;

import java.lang.annotation.*;

/**
 * 外部服务实现注解bean 引用
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OuterServiceRef {

}
