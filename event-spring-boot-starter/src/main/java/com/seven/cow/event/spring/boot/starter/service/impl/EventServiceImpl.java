package com.seven.cow.event.spring.boot.starter.service.impl;

import com.seven.cow.event.spring.boot.starter.service.EventRunnable;
import com.seven.cow.event.spring.boot.starter.service.EventService;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import com.seven.cow.spring.boot.autoconfigure.util.VUtils;
import org.springframework.core.Ordered;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {

    @Resource
    private ExecutorService businessEventExecutorService;

    private static final MultiValueMap<String, EventRunnable> eventRunnableMap = new LinkedMultiValueMap<>();

    @Override
    public void publish(String eventCode, Object message) {
        publish(eventCode, message, message.getClass().getTypeName(), true);
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
                VUtils.choose(() -> eventRunnableMap.containsKey(key) ? 0 : 1)
                        .handle(() -> {
                            List<EventRunnable> eventBurnables = eventRunnableMap.get(key);
                            List<EventRunnable> orderEventBurnables = eventBurnables.stream().sorted(Comparator.comparing(Ordered::getOrder)).collect(Collectors.toList());
                            for (EventRunnable orderEventBurnable : orderEventBurnables) {
                                orderEventBurnable.run(message);
                            }
                        });
        if (isAsync) {
            businessEventExecutorService.execute(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void registerHandler(String eventCode, String messageType, EventRunnable runnable) {
        LoggerUtils.info("register event code handler【" + eventCode + "】:" + runnable);
        eventRunnableMap.add((eventCode + ":" + messageType), runnable);
    }
}
