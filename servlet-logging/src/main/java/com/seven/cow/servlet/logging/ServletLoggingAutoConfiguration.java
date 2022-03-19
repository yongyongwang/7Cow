package com.seven.cow.servlet.logging;

import com.seven.cow.servlet.logging.aop.RequestAspect;
import com.seven.cow.servlet.logging.filters.RequestContextFilter;
import com.seven.cow.servlet.logging.properties.LoggingProperties;
import com.seven.cow.servlet.logging.service.ResponseFilterService;
import com.seven.cow.servlet.logging.service.impl.DefaultResponseFilterServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/24 9:30
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class ServletLoggingAutoConfiguration {

    @Bean("x-requestContextFilter")
    public RequestContextFilter requestContextFilter() {
        return new RequestContextFilter();
    }

    @ConditionalOnClass(name = "org.aspectj.lang.annotation.Aspect")
    @Bean("x-requestAspect")
    @ConditionalOnMissingBean
    public RequestAspect requestAspect() {
        return new RequestAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResponseFilterService responseFilterService() {
        return new DefaultResponseFilterServiceImpl();
    }

}
