package com.seven.cow.spring.boot.autoconfigure.exception;

import com.seven.cow.spring.boot.autoconfigure.entity.BaseError;
import com.seven.cow.spring.boot.autoconfigure.entity.Error;

public class BizException extends RuntimeException implements Error {

    private final Error error;

    public BizException(String message) {
        super(message);
        this.error = new Error() {
            @Override
            public String getErrorCode() {
                return null;
            }

            @Override
            public String getErrorMsg() {
                return message;
            }
        };
    }

    @SuppressWarnings("rawtypes")
    public BizException(BaseError baseError) {
        super(baseError.get().getErrorMsg());
        this.error = baseError.get();
    }

    @SuppressWarnings("rawtypes")
    public BizException(BaseError baseError, Throwable cause) {
        super(baseError.get().getErrorMsg(), cause);
        this.error = baseError.get();
    }

    @Override
    public String getErrorCode() {
        return error.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return error.getErrorMsg();
    }
}
