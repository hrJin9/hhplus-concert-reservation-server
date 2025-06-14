package kr.hhplus.be.server.point.web.request;

import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.common.enums.TransactionType;
import kr.hhplus.be.server.point.service.dto.ChargePointCommand;

public record ChargePointRequest(
        @NotNull(message = "포인트 충전금액은 필수 입력값입니다.")
        Long amount
) {
    public ChargePointCommand toCommand() {
        return new ChargePointCommand(
                amount
        );
    }
}
