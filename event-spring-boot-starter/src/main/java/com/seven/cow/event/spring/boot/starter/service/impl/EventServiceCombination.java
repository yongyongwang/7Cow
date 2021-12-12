package com.seven.cow.event.spring.boot.starter.service.impl;

import com.seven.cow.event.spring.boot.starter.service.EventCallbackService;
import com.seven.cow.event.spring.boot.starter.service.EventService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

public class EventServiceCombination implements InitializingBean {

    @Resource
    private List<EventCallbackService> eventCallbackServices;

    @Resource
    private EventService eventService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(eventCallbackServices)) {
            for (EventCallbackService eventCallbackService : eventCallbackServices) {
                eventService.registerHandler(eventCallbackService.eventCode(), eventCallbackService::run);
            }
        }
    }
}
