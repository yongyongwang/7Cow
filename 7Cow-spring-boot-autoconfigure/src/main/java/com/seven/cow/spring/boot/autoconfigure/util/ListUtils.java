package com.seven.cow.spring.boot.autoconfigure.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ListUtils {

    private ListUtils() {

    }

    /**
     * 分割list为几个list
     *
     * @param list 源list
     * @param max  分割list大小
     * @param <T>  list 包含对象
     * @return list
     */
    public static <T> List<List<T>> splitList(List<T> list, int max) {
        int len = list.size();
        int limit = (len + max - 1) / max;
        return Stream.iterate(0, n -> n + 1)
                .limit(limit).parallel()
                .map(a -> list.stream().skip(a * max).limit(max)
                        .parallel().collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * 获取list交集
     *
     * @param list1 集合1
     * @param list2 集合2
     * @param <T>   list 包含对象
     * @return list
     */
    public static <T> List<T> intersectionList(List<T> list1, List<T> list2) {
        return list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());
    }

    /**
     * 获取list差集
     *
     * @param list1 集合1
     * @param list2 集合2
     * @param <T>   list 包含对象
     * @return list
     */
    public static <T> List<T> diffList1ToList2(List<T> list1, List<T> list2) {
        return list1.stream()
                .filter(item -> !list2.contains(item))
                .collect(Collectors.toList());
    }

    /**
     * 数组转 list
     *
     * @param arrays 数组
     * @param <T>    数组对象
     * @return 不可变 list
     */
    @SafeVarargs
    public static <T> List<T> arrayToNoModifyList(T... arrays) {
        return Arrays.asList(arrays);
    }

    /**
     * 数组转 list
     *
     * @param arrays 数组
     * @param <T>    数组对象
     * @return 可变 list
     */
    @SafeVarargs
    public static <T> List<T> arrayToList(T... arrays) {
        return Stream.of(arrays).collect(Collectors.toList());
    }

}
