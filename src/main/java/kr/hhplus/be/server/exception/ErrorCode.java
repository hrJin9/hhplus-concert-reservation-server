package kr.hhplus.be.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    QUEUE_TOKEN_MISSING("Q001", "대기열 토큰이 누락되었습니다."),
    QUEUE_TOKEN_INVALID("Q003", "대기열 토큰이 유효하지 않습니다."),
    QUEUE_TOKEN_EXPIRED("Q004", "대기열 토큰이 만료되었습니다."),
    QUEUE_TOKEN_NOT_AVAILABLE("Q005", "대기열이 진입 가능한 상태가 아닙니다."),

    POINT_NOT_FOUND("P001", "포인트 정보가 존재하지 않습니다. 충전 후 다시 시도해주세요."),
    INSUFFICIENT_POINT("P002", "잔액이 부족합니다."),

    USER_NOT_FOUND("U001", "사용자를 찾을 수 없습니다."),

    SEAT_NOT_FOUND("S001", "좌석을 찾을 수 없습니다."),
    SEAT_NOT_AVAILABLE("S002", "예약 가능한 좌석이 아닙니다."),
    SEAT_NOT_ACCEPTABLE("S003", "이미 선택된 좌석입니다."),

    CONCERT_NOT_FOUND("C001", "콘서트 정보가 존재하지 않습니다.");

    private final String code;
    private final String message;
}
