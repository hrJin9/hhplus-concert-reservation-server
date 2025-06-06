package kr.hhplus.be.server.domain.exception;

import kr.hhplus.be.server.exception.ErrorCode;

public class InsufficientPointException extends RuntimeException {
    private final ErrorCode errorCode;

    public InsufficientPointException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
