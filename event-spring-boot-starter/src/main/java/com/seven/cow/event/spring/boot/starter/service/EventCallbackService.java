package com.seven.cow.event.spring.boot.starter.service;

public interface EventCallbackService {

    String eventCode();

    void run(Object message);

}
