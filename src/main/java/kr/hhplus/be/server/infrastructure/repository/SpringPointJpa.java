package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.infrastructure.persistence.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringPointJpa extends JpaRepository<PointEntity, Long> {
    Optional<Point> findByUserId(Long userId);
}
