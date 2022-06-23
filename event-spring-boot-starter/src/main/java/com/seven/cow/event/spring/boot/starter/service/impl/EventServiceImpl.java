package com.seven.cow.event.spring.boot.starter.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.seven.cow.event.spring.boot.starter.service.EventRunnable;
import com.seven.cow.event.spring.boot.starter.service.EventService;
import com.seven.cow.event.spring.boot.starter.util.EventUtils;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import com.seven.cow.spring.boot.autoconfigure.util.VUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class EventServiceImpl implements EventService {

    @Resource
    private ExecutorService businessEventExecutorService;

    @Override
    public void publish(String eventCode, Object message) {
        publish(eventCode, message, null == message ? "" : message.getClass().getTypeName(), true);
    }

    @Override
    public <T> void publish(String eventCode, Object message, TypeReference<T> messageType) {
        publish(eventCode, message, messageType.getType().getTypeName(), true);
    }

    @Override
    public void publish(String eventCode, Object message, String messageType, boolean isAsync) {
        LoggerUtils.info("publish event code【" + eventCode + "】:" + message);
        String key = StringUtils.isEmpty(messageType) ? eventCode : eventCode + ":" + messageType;
        Runnable runnable = () ->
                VUtils.choose(() -> EventUtils.containsEvent(key) ? 0 : 1)
                        .handle(() -> {
                            List<EventRunnable> orderEventBurnables = EventUtils.takeEvents(key);
                            for (EventRunnable orderEventBurnable : orderEventBurnables) {
                                orderEventBurnable.run(message);
                            }
                        }, () -> LoggerUtils.info("No Event 【" + key + "】 Handler!"));
        if (isAsync) {
            businessEventExecutorService.execute(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void registerHandler(String eventCode, String messageType, EventRunnable runnable) {
        String key = StringUtils.isEmpty(messageType) ? eventCode : eventCode + ":" + messageType;
        EventUtils.registerEvent(key, runnable);
    }
}
