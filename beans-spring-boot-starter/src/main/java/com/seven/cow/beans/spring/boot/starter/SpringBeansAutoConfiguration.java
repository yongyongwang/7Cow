package com.seven.cow.beans.spring.boot.starter;

import com.seven.cow.beans.spring.boot.starter.layered.BusinessContentBootstrapper;
import com.seven.cow.beans.spring.boot.starter.properties.BeansProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BeansProperties.class)
public class SpringBeansAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BusinessContentBootstrapper businessContentBootstrapper() {
        return new BusinessContentBootstrapper();
    }

}
