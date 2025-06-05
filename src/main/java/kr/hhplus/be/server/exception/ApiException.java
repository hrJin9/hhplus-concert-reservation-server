package kr.hhplus.be.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final LocalDateTime time;

    public ApiException(HttpStatus status, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = status;
        this.time = LocalDateTime.now();
    }
}
