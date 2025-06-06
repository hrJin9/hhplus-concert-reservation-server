package kr.hhplus.be.server.point.web.response;

import kr.hhplus.be.server.point.service.dto.PointResult;

public record PointResponse(
        Long userId,
        Long pointBefore,
        Long amount,
        Long pointAfter
) {
    public static PointResponse from(PointResult pointResult) {
        return new PointResponse(
                pointResult.userId(),
                pointResult.pointBefore(),
                pointResult.amount(),
                pointResult.pointAfter()
        );
    }
}
