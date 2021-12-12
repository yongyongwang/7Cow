package com.seven.cow.event.spring.boot.starter.service;

public interface EventService {

    void publish(String eventCode, Object message);

    void registerHandler(String eventCode, EventRunnable runnable);

}
