package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.exception.PointNotFoundException;
import kr.hhplus.be.server.domain.model.Point;
import kr.hhplus.be.server.domain.repository.PointRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import org.springframework.stereotype.Repository;

@Repository
public class PointJpaRepository implements PointRepository {
    private final SpringPointJpa jpa;

    public PointJpaRepository(SpringPointJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Point findByUserId(Long userId) {
        return jpa.findByUserId(userId)
                .orElseThrow(() -> new PointNotFoundException(ErrorCode.POINT_NOT_FOUND));
    }

    @Override
    public void save(Point point) {
        jpa.save(point);
    }

    @Override
    public Point findOrCreatePoint(Long userId) {
        return jpa.findByUserId(userId)
                .orElse(Point.create(userId));
    }
}
