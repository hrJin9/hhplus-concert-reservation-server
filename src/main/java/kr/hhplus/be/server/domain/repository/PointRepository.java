package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.domain.model.Point;

public interface PointRepository {
    Point findByUserId(Long userId);

    void save(Point point);

    Point findOrCreatePoint(Long userId);
}
