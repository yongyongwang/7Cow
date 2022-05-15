package com.seven.cow.servlet.validator.exception;

import com.seven.cow.spring.boot.autoconfigure.entity.BaseError;
import com.seven.cow.spring.boot.autoconfigure.exception.BizException;

public class ValidationException extends BizException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(BaseError baseError) {
        super(baseError);
    }

    public ValidationException(BaseError baseError, Throwable cause) {
        super(baseError, cause);
    }

}
