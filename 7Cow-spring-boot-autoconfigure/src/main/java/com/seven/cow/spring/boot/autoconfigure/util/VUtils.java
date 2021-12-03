package com.seven.cow.spring.boot.autoconfigure.util;

public final class VUtils {

    public static ChooseHandler choose(Choose choose) {
        return handlers -> {
            int index = choose.index();
            if (index < handlers.length) {
                handlers[index].run();
            }
        };
    }

}
