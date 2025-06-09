package kr.hhplus.be.server.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        HttpStatus status,
        String message,
        LocalDateTime time,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<String> errors
) {
}
