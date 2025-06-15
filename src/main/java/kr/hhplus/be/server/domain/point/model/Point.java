package kr.hhplus.be.server.domain.point.model;

import kr.hhplus.be.server.domain.point.exception.InsufficientPointException;
import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;
import kr.hhplus.be.server.exception.ErrorCode;

public class Point{
    private Long id;

    private final Long userId;

    private Long point;

    protected Point(Long id, Long userId, Long point) {
        this.id = id;
        this.userId = userId;
        this.point = point;
    }

    public static Point create(Long userId) {
        return new Point(
                null,
                userId,
                0L
        );
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPoint() {
        return point;
    }

    public static Point save(Long userId, Long point) {
        return new Point(
                null,
                userId,
                point
        );
    }

    public PointHistory charge(Long amount) {
        Long pointBefore = this.point;
        this.point += amount;

        return PointHistory.charge(
                this.userId,
                pointBefore,
                amount,
                this.point
        );
    }

    public PointHistory use(Long amount) {
        if(this.point < amount) {
            throw new InsufficientPointException(ErrorCode.INSUFFICIENT_POINT);
        }
        Long pointBefore = this.point;
        this.point -= amount;

        return PointHistory.use(
                this.userId,
                pointBefore,
                amount,
                this.point
        );
    }

    public void assignId(Long id) {
        this.id = id;
    }
}
