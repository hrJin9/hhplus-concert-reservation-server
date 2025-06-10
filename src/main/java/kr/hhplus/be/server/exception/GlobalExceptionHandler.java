package kr.hhplus.be.server.exception;

import kr.hhplus.be.server.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        log.warn("ApiException 발생 - status: {}, message: {}, time: {}",
                e.getStatus(), e.getMessage(), e.getTime());

        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getStatus(), e.getMessage(), e.getTime(), null));
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException e) {
        log.warn("DomainException 발생 - status: {}, message: {}, time: {}",
                e.getStatus(), e.getMessage(), e.getTime());

        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getStatus(), e.getMessage(), e.getTime(), null));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->  String.format("[%s] %s", error.getField(), error.getDefaultMessage()))
                .toList();
        log.warn("Validation 실패 - errors: {}", errorMessages);

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        "Validation Failed",
                        LocalDateTime.now(),
                        errorMessages
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        log.error("Unexpected error 발생", e);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal server error",
                        LocalDateTime.now(),
                        null
                ));
    }
}
