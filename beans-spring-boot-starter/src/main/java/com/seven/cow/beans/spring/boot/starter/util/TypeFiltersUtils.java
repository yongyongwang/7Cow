package com.seven.cow.beans.spring.boot.starter.util;

import com.seven.cow.beans.spring.boot.starter.properties.TypeFiltersProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.core.type.filter.*;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.regex.Pattern;

public abstract class TypeFiltersUtils {

    public static void processTypeFilter(TypeFiltersProperties typeFiltersProperties, List<TypeFilter> typeFilters, ClassLoader classLoader) {
        if (null == typeFiltersProperties) {
            return;
        }
        List<Class<Annotation>> annotations = typeFiltersProperties.getAnnotation();
        if (!CollectionUtils.isEmpty(annotations)) {
            for (Class<Annotation> annotation : annotations) {
                typeFilters.add(new AnnotationTypeFilter(annotation));
            }
        }
        List<Class<?>> assignables = typeFiltersProperties.getAssignable();
        if (!CollectionUtils.isEmpty(assignables)) {
            for (Class<?> assignable : assignables) {
                typeFilters.add(new AssignableTypeFilter(assignable));
            }
        }
        List<String> aspectjs = typeFiltersProperties.getAspectj();
        if (!CollectionUtils.isEmpty(aspectjs)) {
            for (String aspectj : aspectjs) {
                typeFilters.add(new AspectJTypeFilter(aspectj, classLoader));
            }
        }
        List<String> regexs = typeFiltersProperties.getRegex();
        if (!CollectionUtils.isEmpty(regexs)) {
            for (String regex : regexs) {
                Pattern pattern = Pattern.compile(regex);
                typeFilters.add(new RegexPatternTypeFilter(pattern));
            }
        }
        List<Class<TypeFilter>> customs = typeFiltersProperties.getCustom();
        if (!CollectionUtils.isEmpty(customs)) {
            for (Class<TypeFilter> custom : customs) {
                TypeFilter typeFilter = BeanUtils.instantiateClass(custom);
                typeFilters.add(typeFilter);
            }
        }
    }

}
