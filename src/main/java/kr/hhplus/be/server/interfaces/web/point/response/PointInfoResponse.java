package kr.hhplus.be.server.interfaces.web.point.response;

import kr.hhplus.be.server.application.point.dto.PointResult;

public record PointInfoResponse(
        Long pointBefore,
        Long amount,
        Long pointAfter
) {
    public static PointInfoResponse from(PointResult result) {
        return new PointInfoResponse(
                result.pointBefore(),
                result.amount(),
                result.pointAfter()
        );
    }
}
