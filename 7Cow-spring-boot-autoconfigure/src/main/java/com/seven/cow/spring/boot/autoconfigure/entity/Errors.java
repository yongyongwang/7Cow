package com.seven.cow.spring.boot.autoconfigure.entity;

public enum Errors implements BaseError<Errors>, Error {
    OK("00000", "ok");

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
