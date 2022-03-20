package com.seven.cow.servlet.validator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "validator")
public class ValidatorProperties {

    /**
     * 请求路径表达式
     */
    private String requestPattern;

    /**
     * 字段校验规则
     */
    private List<Field> fields;

    public String getRequestPattern() {
        return requestPattern;
    }

    public void setRequestPattern(String requestPattern) {
        this.requestPattern = requestPattern;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
