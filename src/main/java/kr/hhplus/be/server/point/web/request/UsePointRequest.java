package kr.hhplus.be.server.point.web.request;

import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.point.service.dto.UsePointCommand;

public record UsePointRequest(
        Long userId,

        @NotNull(message = "포인트 사용금액은 필수 입력값입니다.")
        Long amount
) {
    public UsePointCommand toCommand() {
        return new UsePointCommand(
                userId,
                amount
        );
    }
}
