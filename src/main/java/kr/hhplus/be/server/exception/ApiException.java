package kr.hhplus.be.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final LocalDateTime time;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.time = LocalDateTime.now();
    }
}
