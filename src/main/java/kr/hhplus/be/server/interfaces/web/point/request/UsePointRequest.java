package kr.hhplus.be.server.interfaces.web.point.request;

import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.application.point.dto.UsePointCommand;

public record UsePointRequest(
        @NotNull(message = "포인트 사용금액은 필수 입력값입니다.")
        Long amount
) {
    public UsePointCommand toCommand() {
        return new UsePointCommand(
                amount
        );
    }
}
