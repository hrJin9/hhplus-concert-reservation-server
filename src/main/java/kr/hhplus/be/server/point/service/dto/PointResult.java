package kr.hhplus.be.server.point.service.dto;

import kr.hhplus.be.server.point.domain.PointHistory;

public record PointResult(
        Long userId,
        Long pointBefore,
        Long amount,
        Long pointAfter
) {
    public static PointResult of(PointHistory history) {
        return new PointResult(
                history.getUserId(),
                history.getPointBefore(),
                history.getAmount(),
                history.getPointAfter()
        );
    }
}
