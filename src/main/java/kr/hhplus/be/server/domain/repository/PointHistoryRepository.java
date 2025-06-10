package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.domain.model.PointHistory;

public interface PointHistoryRepository {
    void save(PointHistory history);
}
