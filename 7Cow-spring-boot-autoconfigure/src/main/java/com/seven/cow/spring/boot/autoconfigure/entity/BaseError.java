package com.seven.cow.spring.boot.autoconfigure.entity;

@FunctionalInterface
public interface BaseError<T extends Enum<T>> {

    Error get();

}
