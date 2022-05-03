package com.seven.cow.spring.boot.autoconfigure.entity;

import java.beans.Transient;

public class ResponseData<T> {

    private String state;

    private String errorCode;

    private String errorMsg;

    private T data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseData<T> errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ResponseData<T> errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public static <T> ResponseData<T> ok(T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setState("ok");
        responseData.setData(data);
        return responseData;
    }

    public static <T> ResponseData<T> ok() {
        return ok(null);
    }

    public static <T> ResponseData<T> fail() {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setState("fail");
        return responseData;
    }

    public static <T> ResponseData<T> fail(String errorCode, String errorMsg) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setState("fail");
        responseData.setErrorCode(errorCode);
        responseData.setErrorMsg(errorMsg);
        return responseData;
    }

    @Transient
    public Boolean isOk() {
        return "ok".equalsIgnoreCase(this.state);
    }
}
