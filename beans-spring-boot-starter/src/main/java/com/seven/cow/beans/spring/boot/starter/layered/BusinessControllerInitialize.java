package com.seven.cow.beans.spring.boot.starter.layered;

import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.MethodIntrospector;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 初始化业务 @Controller 接口
 */
public class BusinessControllerInitialize implements ApplicationListener<ContextRefreshedEvent> {

    private final static Map<Class<?>, Map<Method, String>> MAPPING_LOGGING = new ConcurrentHashMap<>();

    private final static AtomicInteger atomicInteger = new AtomicInteger(0);

    private final static ReentrantLock LOCK = new ReentrantLock();

    @Autowired
    private ApplicationContext context;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        if (context.equals(applicationContext)) {
            return;
        }
        Map<String, Object> mappingMap = applicationContext.getBeansWithAnnotation(Controller.class);
        RequestMappingHandlerMapping requestMappingHandlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        Method getMappingForMethod = ReflectionUtils.findMethod(RequestMappingHandlerMapping.class, "getMappingForMethod", Method.class, Class.class);
        mappingMap.forEach((key, handler) ->
        {
            assert getMappingForMethod != null;
            getMappingForMethod.setAccessible(true);
            Class<?> handlerType = handler.getClass();
            if (handlerType != null) {
                Class<?> userType = ClassUtils.getUserClass(handlerType);
                Map<Method, RequestMappingInfo> methods = MethodIntrospector.selectMethods(userType, (MethodIntrospector.MetadataLookup<RequestMappingInfo>) method ->
                {
                    try {
                        return (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, method, userType);
                    } catch (Throwable ex) {
                        throw new IllegalStateException("Invalid mapping on handler class [" +
                                userType.getName() + "]: " + method, ex);
                    }
                });
                Map<Method, String> mappingLogging = new HashMap<>(methods.size());
                methods.forEach((method, mapping) ->
                {
                    Method invocableMethod = AopUtils.selectInvocableMethod(method, userType);
                    try {
                        LOCK.lock();
                        Set<RequestMappingInfo> requestMappingInfoSet = new HashSet<>(requestMappingHandlerMapping.getHandlerMethods().keySet());
                        for (RequestMappingInfo requestMappingInfo : requestMappingInfoSet) {
                            if (requestMappingInfo.equals(mapping)) {
                                requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                            }
                        }
                        requestMappingHandlerMapping.registerMapping(mapping, handler, invocableMethod);
                        mappingLogging.put(method, mapping.toString());
                    } catch (Exception ex) {
                        LoggerUtils.error(ex.getMessage(), ex);
                    } finally {
                        LOCK.unlock();
                    }
                });
                MAPPING_LOGGING.put(userType, mappingLogging);
            }
        });
        atomicInteger.incrementAndGet();
        synchronized (atomicInteger) {
            atomicInteger.notify();
        }
    }

    public static void loggingMapping(int contentTotal) {
//        for (; ; ) {
//            synchronized (atomicInteger) {
//                if (contentTotal == atomicInteger.get()) {
//                    break;
//                } else {
//                    try {
//                        atomicInteger.wait();
//                    } catch (InterruptedException e) {
//                        LoggerUtils.error(e.getMessage(), e);
//                    }
//                }
//            }
//        }
//        StringBuilder sb = new StringBuilder();
//        MAPPING_LOGGING.forEach((userType, methods) ->
//        {
//            sb.append(LoggerUtils.formatMappings(userType, methods));
//        });
//        LoggerUtils.info(sb.toString());
    }

}
