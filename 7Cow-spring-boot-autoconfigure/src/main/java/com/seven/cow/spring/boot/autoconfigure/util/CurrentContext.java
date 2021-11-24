package com.seven.cow.spring.boot.autoconfigure.util;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.concurrent.ConcurrentHashMap;

import static com.seven.cow.spring.boot.autoconfigure.constant.Conts.*;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/23 11:22
 * @version: 1.0
 */
public final class CurrentContext extends ConcurrentHashMap<String, Object> {

    private CurrentContext() {

    }

    private static final TransmittableThreadLocal<? extends CurrentContext> currentContext = new TransmittableThreadLocal<CurrentContext>() {
        @Override
        protected CurrentContext initialValue() {
            return new CurrentContext();
        }
    };

    public static CurrentContext currentContext() {
        return currentContext.get();
    }

    @Override
    public Object put(String key, Object value) {
        if (key != null && value != null) {
            return super.put(key, value);
        } else {
            LoggerUtils.info("current context set key: " + key + " failure!");
            return null;
        }
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        if (key != null && value != null) {
            return super.putIfAbsent(key, value);
        } else {
            LoggerUtils.info("current context set key: " + key + " failure!");
            return null;
        }
    }

    public static void remove() {
        currentContext().clear();
        currentContext.remove();
    }

    public static String getHeader(String key) {
        return (String) currentContext().getOrDefault(X_CURRENT_HEADERS + SPLIT_COLON + key, "");
    }

    public static String getParameter(String parameterName) {
        return (String) currentContext().getOrDefault((X_CURRENT_REQUEST_PARAMETERS + SPLIT_COLON + parameterName), "");
    }

    public static <T> T take(String key) {
        return take(key, null);
    }

    public static <T> T take(String key, T defaultValue) {
        return (T) currentContext().getOrDefault(key, defaultValue);
    }

    public static boolean existsKey(String key) {
        return currentContext().containsKey(key);
    }

    public static void set(String key, Object value) {
        currentContext().put(key, value);
    }

    public static void setIfAbsent(String key, Object value) {
        currentContext().putIfAbsent(key, value);
    }

}
