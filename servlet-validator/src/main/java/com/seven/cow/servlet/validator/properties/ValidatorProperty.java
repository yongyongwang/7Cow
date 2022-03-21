package com.seven.cow.servlet.validator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties
public class ValidatorProperty {

    /**
     * 请求路径表达式
     */
    private String requestPattern;

    /**
     * 参数断言表达式
     */
    private List<Expression> assertExpressions;

    public String getRequestPattern() {
        return requestPattern;
    }

    public void setRequestPattern(String requestPattern) {
        this.requestPattern = requestPattern;
    }

    public List<Expression> getAssertExpressions() {
        return assertExpressions;
    }

    public void setAssertExpressions(List<Expression> assertExpressions) {
        this.assertExpressions = assertExpressions;
    }
}
