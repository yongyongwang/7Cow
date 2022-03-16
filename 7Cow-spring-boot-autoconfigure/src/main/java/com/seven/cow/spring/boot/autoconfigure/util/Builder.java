package com.seven.cow.spring.boot.autoconfigure.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 通用的 Builder 模式构建器
 *
 * @param <T>
 */
public final class Builder<T> {
    private final Supplier<T> instantiator;
    private final List<Consumer<T>> modifiers = new ArrayList<>();

    private Builder() {
        this.instantiator = null;
    }

    private Builder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    private T instance;

    public static <T> Builder<T> of(Supplier<T> instantiator) {
        return new Builder<>(instantiator);
    }

    public static <T> Builder<T> of(T instance) {
        Builder<T> builder = new Builder<>();
        builder.instance = instance;
        return builder;
    }

    public <P1> Builder<T> with(Consumer1<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        modifiers.add(c);
        return this;
    }

    public <P1, P2> Builder<T> with(Consumer2<T, P1, P2> consumer, P1 p1, P2 p2) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2);
        modifiers.add(c);
        return this;
    }

    public <P1, P2, P3> Builder<T> with(Consumer3<T, P1, P2, P3> consumer, P1 p1, P2 p2, P3 p3) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2, p3);
        modifiers.add(c);
        return this;
    }

    public T build() {
        T value = null != instantiator ? instantiator.get() : instance;
        modifiers.forEach(modifier -> modifier.accept(value));
        modifiers.clear();
        return value;
    }

    public T newBuild() {
        T value = null != instantiator ? instantiator.get() : instance;
        T newValue;
        Class<T> targetClass = (Class<T>) value.getClass();
        newValue = BeanUtils.newInstance(targetClass);
        assert newValue != null;
        BeanUtils.copyProperties(value, newValue);
        T finalNewValue = newValue;
        modifiers.forEach(modifier -> modifier.accept(finalNewValue));
        modifiers.clear();
        return newValue;
    }

    /**
     * 1 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer1<T, P1> {
        void accept(T t, P1 p1);
    }

    /**
     * 2 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer2<T, P1, P2> {
        void accept(T t, P1 p1, P2 p2);
    }

    /**
     * 3 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer3<T, P1, P2, P3> {
        void accept(T t, P1 p1, P2 p2, P3 p3);
    }

}
