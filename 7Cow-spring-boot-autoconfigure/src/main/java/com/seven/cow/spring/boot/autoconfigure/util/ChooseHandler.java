package com.seven.cow.spring.boot.autoconfigure.util;

@FunctionalInterface
public interface ChooseHandler {

    void handle(Runnable... handlers);

}
