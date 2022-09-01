package com.seven.cow.spring.boot.autoconfigure.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * 类型判断
 */
public class Types {

    private static final Class<?>[] BASE_TYPE = new Class<?>[]{
            byte.class, boolean.class, char.class, short.class,
            int.class, long.class, float.class, double.class,

            Byte.class, Boolean.class, Character.class, Short.class,
            Integer.class, Long.class, Float.class, Double.class,
    };

    private static final Class<?>[] ATOMIC_TYPES = new Class<?>[]{
            byte.class, boolean.class, char.class, short.class,
            int.class, long.class, float.class, double.class,

            Byte.class, Boolean.class, Character.class, Short.class,
            Integer.class, Long.class, Float.class, Double.class,

            void.class, Void.class, String.class, Number.class
    };

    private Types() {
        throw new UnsupportedOperationException();
    }

    public static boolean isInstanceOf(Object value, Class<?>... classes) {
        Class<?> valueClass = getValueClass(value);

        return Arrays.stream(classes).anyMatch(matchClass -> matchClass.isAssignableFrom(valueClass));
    }

    public static boolean isByte(Object value) {
        return isInstanceOf(getValueClass(value), byte.class, Byte.class);
    }

    public static boolean isBoolean(Object value) {
        return isInstanceOf(getValueClass(value), boolean.class, Boolean.class);
    }

    public static boolean isChar(Object value) {
        return isInstanceOf(getValueClass(value), char.class, Character.class);
    }

    public static boolean isShort(Object value) {
        return isInstanceOf(getValueClass(value), short.class, Short.class);
    }

    public static boolean isInt(Object value) {
        return isInstanceOf(getValueClass(value), int.class, Integer.class);
    }

    public static boolean isLong(Object value) {
        return isInstanceOf(getValueClass(value), long.class, Long.class);
    }

    public static boolean isFloat(Object value) {
        return isInstanceOf(getValueClass(value), float.class, Float.class);
    }

    public static boolean isDouble(Object value) {
        return isInstanceOf(getValueClass(value), double.class, Double.class);
    }

    public static boolean isVoid(Object value) {
        return isInstanceOf(getValueClass(value), void.class, Void.class);
    }

    /**
     * 判断是否八大基础类型
     *
     * @param value 实例或者类对象
     * @return true 即代表 value 描述的变量属于八大基础类型
     */
    public static boolean isBaseType(Object value) {
        return isInstanceOf(value, BASE_TYPE);
    }

    /**
     * 判断是否基础类型或 String, Void, AtomicInteger 这类不可再分的类型
     *
     * @param value 实例或者类对象
     * @return true 即代表 value 描述的变量属于不可再分的类型
     */
    public static boolean isAtomicType(Object value) {
        return isInstanceOf(value, ATOMIC_TYPES);
    }

    public static boolean hasSameBaseType(Object o1, Object o2) {
        return (isInt(o1) && isInt(o2))
                || (isLong(o1) && isLong(o2))
                || (isFloat(o1) && isFloat(o2))
                || (isDouble(o1) && isDouble(o2))

                || (isBoolean(o1) && isBoolean(o2))
                || (isShort(o1) && isShort(o2))
                || (isChar(o1) && isChar(o2))
                || (isByte(o1) && isByte(o2));
    }

    private static Class<?> getValueClass(Object value) {
        Objects.requireNonNull(value, "value 不能为空");
        return value instanceof Class ? (Class<?>) value : value.getClass();
    }

}
