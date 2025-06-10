package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringPointJpa extends JpaRepository<Point, Long> {
    Optional<Point> findByUserId(Long userId);
}
