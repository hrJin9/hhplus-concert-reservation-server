package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.point.exception.PointNotFoundException;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.infrastructure.persistence.PointEntity;
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
    public void save(Point d) {
        PointEntity e = toEntity(d);
        PointEntity saved = jpa.save(e);
        d.assignId(saved.id);
    }

    @Override
    public Point findOrCreatePoint(Long userId) {
        return jpa.findByUserId(userId)
                .orElse(Point.create(userId));
    }

    private PointEntity toEntity(Point d) {
        PointEntity e = new PointEntity();
        e.id = d.getId();
        e.userId = d.getUserId();
        e.point = d.getPoint();
        return e;
    }
}
