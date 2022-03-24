package com.seven.cow.beans.spring.boot.starter.util;


import java.util.HashMap;
import java.util.Map;

public abstract class OuterServiceUtils {

    private static final Map<Class<?>, Object> outerServiceMap = new HashMap<>();

    public static void put(Class<?> beanClass, Object beanInstance) {
        outerServiceMap.put(beanClass, beanInstance);
    }

    public static Object take(Class<?> beanClass) {
        return outerServiceMap.get(beanClass);
    }

}
