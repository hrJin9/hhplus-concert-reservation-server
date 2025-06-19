package kr.hhplus.be.server.domain.pointHistory.repository;

import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;

public interface PointHistoryRepository {
    PointHistory save(PointHistory history);
}
