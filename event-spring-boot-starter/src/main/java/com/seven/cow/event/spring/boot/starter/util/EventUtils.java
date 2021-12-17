package com.seven.cow.event.spring.boot.starter.util;

import com.seven.cow.event.spring.boot.starter.service.EventRunnable;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.core.Ordered;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EventUtils {

    private static final MultiValueMap<String, EventRunnable> eventRunnableMap = new LinkedMultiValueMap<>();

    public static void registerEvent(String event, EventRunnable runnable) {
        LoggerUtils.info("register event code【" + event + "】handler:" + runnable);
        eventRunnableMap.add(event, runnable);
    }

    public static boolean containsEvent(String event) {
        return eventRunnableMap.containsKey(event);
    }

    public static List<EventRunnable> takeEvents(String event) {
        List<EventRunnable> eventBurnables = eventRunnableMap.get(event);
        LoggerUtils.info("take event【" + event + "】runnable size:" + eventBurnables.size());
        return eventBurnables.stream().sorted(Comparator.comparing(Ordered::getOrder)).collect(Collectors.toList());
    }

}
