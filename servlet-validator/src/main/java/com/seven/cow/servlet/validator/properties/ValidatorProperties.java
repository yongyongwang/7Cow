package com.seven.cow.servlet.validator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "validator")
public class ValidatorProperties {

    /**
     * 校验规则
     */
    private List<ValidatorProperty> rules;

    public List<ValidatorProperty> getRules() {
        return rules;
    }

    public void setRules(List<ValidatorProperty> rules) {
        this.rules = rules;
    }
}
