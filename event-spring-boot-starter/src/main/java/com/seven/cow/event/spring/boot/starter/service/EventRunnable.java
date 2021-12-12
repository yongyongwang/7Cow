package com.seven.cow.event.spring.boot.starter.service;

@FunctionalInterface
public interface EventRunnable {
    void run(Object message);
}
