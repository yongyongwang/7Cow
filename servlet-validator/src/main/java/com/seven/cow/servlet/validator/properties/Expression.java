package com.seven.cow.servlet.validator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Expression {

    /**
     * 表达式
     */
    private String expression;

    /**
     * 校验表达式为 false 时提示消息
     */
    private String message;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
