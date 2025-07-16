package kr.hhplus.be.server.interfaces.web.point.response;

import kr.hhplus.be.server.application.point.dto.PointBalanceInfo;

public record PointBalanceResponse(
        Long point
) {
    public static PointBalanceResponse from(PointBalanceInfo result) {
        return new PointBalanceResponse(
                result.point()
        );
    }
}
