package com.seven.cow.spring.boot.autoconfigure.entity;

import com.seven.cow.spring.boot.autoconfigure.constant.Cants;

import java.util.Collections;
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

    @SuppressWarnings("unchecked")
    public static <T> ResponseCmd<T> ok() {
        return ok(Collections.EMPTY_LIST, 0);
    }

    public static <T> ResponsePagingCmd<T> ok(List<T> data, int total) {
        return (ResponsePagingCmd<T>) new ResponsePagingCmd<T>().total(total).data(data).code(Errors.SUCCESS.getErrorCode()).message(Errors.SUCCESS.getErrorMsg()).state(Cants.REQUEST_OK);
    }

}
