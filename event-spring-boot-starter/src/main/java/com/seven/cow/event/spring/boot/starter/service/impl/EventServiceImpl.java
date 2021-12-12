package com.seven.cow.event.spring.boot.starter.service.impl;

import com.seven.cow.event.spring.boot.starter.service.EventRunnable;
import com.seven.cow.event.spring.boot.starter.service.EventService;
import com.seven.cow.spring.boot.autoconfigure.util.VUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class EventServiceImpl implements EventService {

    @Resource
    private ExecutorService businessEventExecutorService;

    private static final MultiValueMap<String, EventRunnable> eventRunnableMap = new LinkedMultiValueMap<>();

    @Override
    public void publish(String eventCode, Object message) {
        businessEventExecutorService
                .execute(() -> VUtils.choose(() -> eventRunnableMap.containsKey(eventCode) ? 0 : 1)
                        .handle(() -> {
                            List<EventRunnable> eventBurnables = eventRunnableMap.get(eventCode);
                            for (EventRunnable eventRunnable : eventBurnables) {
                                eventRunnable.run(message);
                            }
                        }));
    }

    @Override
    public void registerHandler(String eventCode, EventRunnable runnable) {
        eventRunnableMap.add(eventCode, runnable);
    }
}
