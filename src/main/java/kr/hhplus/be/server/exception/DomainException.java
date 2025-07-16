package kr.hhplus.be.server.exception;

import java.time.LocalDateTime;

public abstract class DomainException extends RuntimeException {
    public abstract ErrorCode errorCode();
    private final LocalDateTime time;

    public DomainException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.time = LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }
}
