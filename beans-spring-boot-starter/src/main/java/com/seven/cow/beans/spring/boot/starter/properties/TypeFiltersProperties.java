package com.seven.cow.beans.spring.boot.starter.properties;

import org.springframework.core.type.filter.TypeFilter;

import java.lang.annotation.Annotation;
import java.util.List;

public class TypeFiltersProperties {

    /**
     * 注解过滤
     */
    private List<Class<Annotation>> annotation;

    /**
     * 类型过滤
     */
    private List<Class<?>> assignable;

    /**
     * aspect 表达式过滤
     */
    private List<String> aspectj;

    /**
     * 正则表达式过滤
     */
    private List<String> regex;

    /**
     * 自定义过滤
     */
    private List<Class<TypeFilter>> custom;

    public List<Class<Annotation>> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(List<Class<Annotation>> annotation) {
        this.annotation = annotation;
    }

    public List<Class<?>> getAssignable() {
        return assignable;
    }

    public void setAssignable(List<Class<?>> assignable) {
        this.assignable = assignable;
    }

    public List<String> getAspectj() {
        return aspectj;
    }

    public void setAspectj(List<String> aspectj) {
        this.aspectj = aspectj;
    }

    public List<String> getRegex() {
        return regex;
    }

    public void setRegex(List<String> regex) {
        this.regex = regex;
    }

    public List<Class<TypeFilter>> getCustom() {
        return custom;
    }

    public void setCustom(List<Class<TypeFilter>> custom) {
        this.custom = custom;
    }
}
