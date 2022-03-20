package com.seven.cow.servlet.validator;

import com.seven.cow.servlet.validator.properties.ValidatorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ValidatorProperties.class)
public class ServletValidatorAutoConfiguration {

}
