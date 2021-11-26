package com.seven.cow.spring.boot.autoconfigure.entity;

import java.util.Collections;
import java.util.List;

public class ResponsePagingData<T> {

    private String state;

    private String errorCode;

    private String errorMsg;

    private int total;

    private List<T> data;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ResponsePagingData<T> errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ResponsePagingData<T> errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public static <T> ResponsePagingData<T> ok(List<T> data, int total) {
        ResponsePagingData<T> responsePagingData = new ResponsePagingData<>();
        responsePagingData.setState("ok");
        responsePagingData.setData(data);
        responsePagingData.setTotal(total);
        return responsePagingData;
    }

    public static <T> ResponsePagingData<T> ok() {
        return ok(Collections.emptyList(), 0);
    }

    public static <T> ResponsePagingData<T> fail() {
        ResponsePagingData<T> responsePagingData = new ResponsePagingData<>();
        responsePagingData.setState("fail");
        responsePagingData.setData(Collections.emptyList());
        return responsePagingData;
    }

}
