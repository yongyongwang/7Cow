package com.seven.cow.servlet.validator;

import com.seven.cow.servlet.validator.aop.ValidatorAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletValidatorAutoConfiguration {

    @ConditionalOnClass(name = "org.aspectj.lang.annotation.Aspect")
    @Bean("x-validatorAspect")
    @ConditionalOnMissingBean
    public ValidatorAspect validatorAspect() {
        return new ValidatorAspect();
    }

}
