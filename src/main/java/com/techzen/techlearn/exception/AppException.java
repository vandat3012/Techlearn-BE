package com.techzen.techlearn.exception;

import com.techzen.techlearn.enums.ErrorCode;
import lombok.*;

@Getter
@Setter
public class AppException extends RuntimeException {

    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}