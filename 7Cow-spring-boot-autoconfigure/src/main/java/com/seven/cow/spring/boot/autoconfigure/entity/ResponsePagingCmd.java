package com.seven.cow.spring.boot.autoconfigure.entity;

import com.seven.cow.spring.boot.autoconfigure.constant.Conts;

import java.util.List;

public class ResponsePagingCmd<T> extends ResponseCmd<List<T>> {

    private int total;

    public int getTotal() {
        return total;
    }

    public ResponsePagingCmd<T> total(int total) {
        this.total = total;
        return this;
    }

    public static <T> ResponsePagingCmd<T> ok(List<T> data, int total) {
        return (ResponsePagingCmd<T>) new ResponsePagingCmd<T>().total(total).data(data).state(Conts.REQUEST_OK);
    }

}
