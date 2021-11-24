package com.seven.cow.servlet.logging;

import com.seven.cow.servlet.logging.aop.RequestAspect;
import com.seven.cow.servlet.logging.filters.RequestContextFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/24 9:30
 * @version: 1.0
 */
@Configuration
public class ServletLoggingAutoConfiguration {

    @Bean("x-requestContextFilter")
    public RequestContextFilter requestContextFilter() {
        return new RequestContextFilter();
    }

    @ConditionalOnClass(name = "org.aspectj.lang.annotation.Aspect")
    @Bean("x-requestAspect")
    public RequestAspect requestAspect() {
        return new RequestAspect();
    }

}
