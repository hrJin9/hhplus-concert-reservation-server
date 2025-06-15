package kr.hhplus.be.server.domain.concert.exception;

import kr.hhplus.be.server.domain.DomainException;
import kr.hhplus.be.server.exception.ErrorCode;

public class ConcertNotFoundException extends DomainException {
    private final ErrorCode errorCode;

    @Override
    public ErrorCode errorCode() {
        return this.errorCode;
    }

    public ConcertNotFoundException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
