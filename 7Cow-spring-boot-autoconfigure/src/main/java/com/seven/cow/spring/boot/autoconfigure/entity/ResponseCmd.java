package com.seven.cow.spring.boot.autoconfigure.entity;

import com.seven.cow.spring.boot.autoconfigure.constant.Conts;

import java.beans.Transient;

public class ResponseCmd<T> {

    private String state;

    private String code;

    private String message;

    private T data;

    public String getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public ResponseCmd<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseCmd<T> code(String code) {
        this.code = code;
        return this;
    }

    public ResponseCmd<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResponseCmd<T> state(String state) {
        this.state = state;
        return this;
    }

    public static <T> ResponseCmd<T> ok(T data) {
        return new ResponseCmd<T>().code(Errors.SUCCESS.getErrorCode()).message(Errors.SUCCESS.getErrorMsg()).state(Conts.REQUEST_OK).data(data);
    }

    public static <T> ResponseCmd<T> ok() {
        return ok(null);
    }

    public static <T> ResponseCmd<T> fail() {
        return fail(Errors.FAIL);
    }

    private static <T> ResponseCmd<T> fail(String code, String message) {
        return new ResponseCmd<T>().state(Conts.REQUEST_FAIL).code(code).message(message);
    }

    @SuppressWarnings("rawtypes")
    public static <T> ResponseCmd<T> fail(BaseError baseError) {
        Error error = baseError.get();
        return fail(error.getErrorCode(), error.getErrorMsg());
    }

    @Transient
    public Boolean isOk() {
        return Conts.REQUEST_OK.equalsIgnoreCase(this.state);
    }
}
