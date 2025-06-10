package kr.hhplus.be.server.application.point.dto;

import kr.hhplus.be.server.domain.model.PointHistory;

public record PointInfo(
        Long pointBefore,
        Long amount,
        Long pointAfter
) {

    public static PointInfo from(PointHistory pointHistory) {
        return new PointInfo(
                pointHistory.getPointBefore(),
                pointHistory.getAmount(),
                pointHistory.getPointAfter()
        );
    }
}
