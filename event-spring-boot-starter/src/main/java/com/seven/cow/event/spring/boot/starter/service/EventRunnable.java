package com.seven.cow.event.spring.boot.starter.service;

import org.springframework.core.Ordered;

public interface EventRunnable extends Ordered {
    void run(Object message);
}
