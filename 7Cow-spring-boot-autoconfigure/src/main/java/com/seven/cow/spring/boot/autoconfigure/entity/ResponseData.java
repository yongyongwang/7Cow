package com.seven.cow.spring.boot.autoconfigure.entity;

import com.seven.cow.spring.boot.autoconfigure.constant.Conts;

import java.beans.Transient;

public class ResponseData<T> {

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

    public ResponseData<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseData<T> errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ResponseData<T> errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ResponseData<T> state(String state) {
        this.state = state;
        return this;
    }

    public static <T> ResponseData<T> ok(T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setState(Conts.REQUEST_OK);
        responseData.setData(data);
        return responseData;
    }

    public static <T> ResponseData<T> ok() {
        return ok(null);
    }

    public static <T> ResponseData<T> fail() {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setState(Conts.REQUEST_FAIL);
        return responseData;
    }

    public static <T> ResponseData<T> fail(String errorCode, String errorMsg) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setState(Conts.REQUEST_FAIL);
        responseData.setErrorCode(errorCode);
        responseData.setErrorMsg(errorMsg);
        return responseData;
    }

    @Transient
    public Boolean isOk() {
        return Conts.REQUEST_OK.equalsIgnoreCase(this.state);
    }
}
