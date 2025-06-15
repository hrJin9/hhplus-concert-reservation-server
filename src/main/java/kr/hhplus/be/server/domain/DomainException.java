package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public abstract class DomainException extends RuntimeException {
    public abstract ErrorCode errorCode();
    private final HttpStatus status;
    private final LocalDateTime time;

    public DomainException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.time = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
