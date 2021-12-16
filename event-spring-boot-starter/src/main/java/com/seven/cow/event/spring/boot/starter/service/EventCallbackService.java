package com.seven.cow.event.spring.boot.starter.service;

public interface EventCallbackService<T> {

    String eventCode();

    void run(T message);

}
