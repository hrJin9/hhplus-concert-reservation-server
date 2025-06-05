package kr.hhplus.be.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> handleBlaBlaException(ApiException e) {

        log.error("status = {}, message = {}, time = {}", e.getStatus() , e.getMessage(), e.getTime());
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getStatus(), e.getMessage(), e.getTime()));
    }
}
