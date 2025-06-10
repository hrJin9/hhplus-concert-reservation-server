package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPointHistoryJpa extends JpaRepository<PointHistory, Long> {
}
