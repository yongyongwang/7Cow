package com.seven.cow.event.spring.boot.starter.annotations;

import java.lang.annotation.*;

/**
 * @ClassName BusinessEventListener
 * @Date 2021/3/15 16:15
 * @Auther wangyongyong
 * @Version 1.0
 * @Description 业务事件监听
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessEventListener
{
    String value() default "";// 事件编码

    int order() default 0;// 执行顺序，值越小，越先执行
}
