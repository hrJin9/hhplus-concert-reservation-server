package kr.hhplus.be.server.point.repository;

import kr.hhplus.be.server.point.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}
