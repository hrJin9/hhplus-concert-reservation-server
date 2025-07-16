package kr.hhplus.be.server.exception;

import java.time.LocalDateTime;

public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final LocalDateTime time;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.time = LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
