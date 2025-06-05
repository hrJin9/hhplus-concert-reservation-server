package kr.hhplus.be.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    POINT_NOT_FOUND("P001", "포인트 정보가 존재하지 않습니다. 충전 후 다시 시도해주세요."),
    INSUFFICIENT_POINT("P002", "잔액이 부족합니다."),

    USER_NOT_FOUND("U001", "사용자를 찾을 수 없습니다.");

    private final String code;
    private final String message;
}
