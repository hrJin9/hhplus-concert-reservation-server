package kr.hhplus.be.server.domain.concertSeat.exception;

import kr.hhplus.be.server.domain.DomainException;
import kr.hhplus.be.server.exception.ErrorCode;

public class ConcertSeatNotFoundException extends DomainException {
    private final ErrorCode errorCode;

    @Override
    public ErrorCode errorCode() {
        return this.errorCode;
    }

    public ConcertSeatNotFoundException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
