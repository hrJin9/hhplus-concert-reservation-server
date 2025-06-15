package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.model.Point;

public interface PointRepository {
    Point findByUserId(Long userId);

    void save(Point point);

    Point findOrCreatePoint(Long userId);
}
