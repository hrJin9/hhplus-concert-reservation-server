package kr.hhplus.be.server.domain.exception;

import kr.hhplus.be.server.exception.ErrorCode;

public class PointNotFoundException extends DomainException {
    private final ErrorCode errorCode;

    @Override
    public ErrorCode errorCode() {
        return this.errorCode;
    }

    public PointNotFoundException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
