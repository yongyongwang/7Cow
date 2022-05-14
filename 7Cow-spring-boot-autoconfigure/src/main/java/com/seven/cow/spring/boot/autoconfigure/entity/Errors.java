package com.seven.cow.spring.boot.autoconfigure.entity;

public enum Errors implements BaseError<Errors>, Error {
    SUCCESS("00000", "请求成功"),
    FAIL("99999", "系统未知错误");

    Errors(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private final String errorCode;

    private final String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public Error get() {
        return this;
    }
}
