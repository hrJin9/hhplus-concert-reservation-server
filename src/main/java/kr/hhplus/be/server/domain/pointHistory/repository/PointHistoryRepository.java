package kr.hhplus.be.server.domain.pointHistory.repository;

import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;

public interface PointHistoryRepository {
    void save(PointHistory history);
}
