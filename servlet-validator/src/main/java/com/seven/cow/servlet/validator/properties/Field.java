package com.seven.cow.servlet.validator.properties;

import java.lang.annotation.Annotation;
import java.util.List;

public class Field {

    /**
     * 字段属性取值表达式
     */
    private String expression;

    /**
     * 字段校验规则
     */
    private List<Class<Annotation>> annotations;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<Class<Annotation>> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Class<Annotation>> annotations) {
        this.annotations = annotations;
    }
}
