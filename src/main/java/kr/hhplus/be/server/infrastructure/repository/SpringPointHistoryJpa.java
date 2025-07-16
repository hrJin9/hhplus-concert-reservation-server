package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.infrastructure.persistence.PointHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPointHistoryJpa extends JpaRepository<PointHistoryEntity, Long> {
}
