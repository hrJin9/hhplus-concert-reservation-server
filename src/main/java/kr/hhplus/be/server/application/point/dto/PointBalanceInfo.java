package kr.hhplus.be.server.application.point.dto;

import kr.hhplus.be.server.domain.model.Point;

public record PointBalanceInfo(
        Long point
) {
    public static PointBalanceInfo from(Point point) {
        return new PointBalanceInfo(
                point.getPoint()
        );
    }

}
