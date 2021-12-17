package com.seven.cow.event.spring.boot.starter.service.impl;

import com.seven.cow.event.spring.boot.starter.service.EventRunnable;
import com.seven.cow.event.spring.boot.starter.service.EventService;
import com.seven.cow.event.spring.boot.starter.util.EventUtils;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import com.seven.cow.spring.boot.autoconfigure.util.VUtils;

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
    public <T> void publish(String eventCode, Object message, EventMsgTypeRef<T> messageType) {
        publish(eventCode, message, messageType.getType().getTypeName(), true);
    }

    @Override
    public void publish(String eventCode, Object message, String messageType, boolean isAsync) {
        LoggerUtils.info("publish event code【" + eventCode + "】:" + message);
        String key = eventCode + ":" + messageType;
        Runnable runnable = () ->
                VUtils.choose(() -> EventUtils.containsEvent(key) ? 0 : 1)
                        .handle(() -> {
                            List<EventRunnable> orderEventBurnables = EventUtils.takeEvents(key);
                            for (EventRunnable orderEventBurnable : orderEventBurnables) {
                                orderEventBurnable.run(message);
                            }
                        }, () -> LoggerUtils.info("No Event Handler Process"));
        if (isAsync) {
            businessEventExecutorService.execute(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void registerHandler(String eventCode, String messageType, EventRunnable runnable) {
        EventUtils.registerEvent((eventCode + ":" + messageType), runnable);
    }
}
