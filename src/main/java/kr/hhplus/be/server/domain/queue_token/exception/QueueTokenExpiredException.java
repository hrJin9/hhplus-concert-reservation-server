package kr.hhplus.be.server.domain.queue_token.exception;

import kr.hhplus.be.server.domain.DomainException;
import kr.hhplus.be.server.exception.ErrorCode;

public class QueueTokenExpiredException extends DomainException {
    private final ErrorCode errorCode;

    @Override
    public ErrorCode errorCode() {
        return this.errorCode;
    }

    public QueueTokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}

