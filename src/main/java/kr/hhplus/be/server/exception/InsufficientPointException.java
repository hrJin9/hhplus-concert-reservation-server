package kr.hhplus.be.server.exception;

import kr.hhplus.be.server.exception.DomainException;
import kr.hhplus.be.server.exception.ErrorCode;

public class InsufficientPointException extends DomainException {
    private final ErrorCode errorCode;

    @Override
    public ErrorCode errorCode() {
        return this.errorCode;
    }

    public InsufficientPointException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
