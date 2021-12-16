package com.seven.cow.event.spring.boot.starter.service;

import com.seven.cow.event.spring.boot.starter.service.impl.EventMsgTypeRef;

public interface EventService {

    void publish(String eventCode, Object message);

    <T> void publish(String eventCode, Object message, EventMsgTypeRef<T> messageType);

    void publish(String eventCode, Object message, String messageType, boolean isAsync);

    void registerHandler(String eventCode, String messageType, EventRunnable runnable);

}
