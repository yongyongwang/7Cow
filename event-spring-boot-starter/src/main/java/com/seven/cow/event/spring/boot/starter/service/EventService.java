package com.seven.cow.event.spring.boot.starter.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface EventService {

    void publish(String eventCode, Object message);

    <T> void publish(String eventCode, Object message, TypeReference<T> messageType);

    void publish(String eventCode, Object message, String messageType, boolean isAsync);

    void registerHandler(String eventCode, String messageType, EventRunnable runnable);

}
