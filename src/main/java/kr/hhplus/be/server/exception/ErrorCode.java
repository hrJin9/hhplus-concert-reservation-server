package kr.hhplus.be.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    QUEUE_TOKEN_MISSING("Q001", HttpStatus.UNAUTHORIZED, "대기열 토큰이 누락되었습니다."),
    QUEUE_TOKEN_INVALID("Q003", HttpStatus.UNAUTHORIZED, "대기열 토큰이 유효하지 않습니다."),
    QUEUE_TOKEN_EXPIRED("Q004", HttpStatus.UNAUTHORIZED, "대기열 토큰이 만료되었습니다."),
    QUEUE_TOKEN_NOT_AVAILABLE("Q005", HttpStatus.UNAUTHORIZED, "대기열이 진입 가능한 상태가 아닙니다."),

    POINT_NOT_FOUND("P001", HttpStatus.NOT_FOUND, "포인트 정보가 존재하지 않습니다. 충전 후 다시 시도해주세요."),
    INSUFFICIENT_POINT("P002", HttpStatus.PAYMENT_REQUIRED, "잔액이 부족합니다."),

    USER_NOT_FOUND("U001", HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."),

    SEAT_NOT_FOUND("S001", HttpStatus.NOT_FOUND, "좌석을 찾을 수 없습니다."),
    SEAT_NOT_AVAILABLE("S002", HttpStatus.BAD_REQUEST, "해당 좌석은 예약할 수 없습니다."),
    SEAT_ALREADY_SELECTED("S003", HttpStatus.BAD_REQUEST, "이미 선택된 좌석입니다."),

    CONCERT_NOT_FOUND("C001", HttpStatus.NOT_FOUND, "콘서트 정보가 존재하지 않습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
