package com.seven.cow.spring.boot.autoconfigure.entity;

import com.seven.cow.spring.boot.autoconfigure.constant.Conts;

import java.beans.Transient;

public class ResponseCmd<T> {

    private String state;

    private String errorCode;

    private String errorMsg;

    private T data;

    public String getState() {
        return state;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public T getData() {
        return data;
    }

    public ResponseCmd<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseCmd<T> errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ResponseCmd<T> errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ResponseCmd<T> state(String state) {
        this.state = state;
        return this;
    }

    public static <T> ResponseCmd<T> ok(T data) {
        return new ResponseCmd<T>().state(Conts.REQUEST_OK).data(data);
    }

    public static <T> ResponseCmd<T> ok() {
        return ok(null);
    }

    public static <T> ResponseCmd<T> fail() {
        return new ResponseCmd<T>().state(Conts.REQUEST_FAIL);
    }

    public static <T> ResponseCmd<T> fail(String errorCode, String errorMsg) {
        return new ResponseCmd<T>().state(Conts.REQUEST_FAIL).errorCode(errorCode).errorMsg(errorMsg);
    }

    @Transient
    public Boolean isOk() {
        return Conts.REQUEST_OK.equalsIgnoreCase(this.state);
    }
}
