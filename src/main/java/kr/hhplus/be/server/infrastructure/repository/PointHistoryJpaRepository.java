package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;
import kr.hhplus.be.server.domain.pointHistory.repository.PointHistoryRepository;
import kr.hhplus.be.server.infrastructure.persistence.PointHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PointHistoryJpaRepository implements PointHistoryRepository {
    private final SpringPointHistoryJpa jpa;

    public PointHistoryJpaRepository(SpringPointHistoryJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public void save(PointHistory d) {
        PointHistoryEntity e = toEntity(d);
        PointHistoryEntity saved = jpa.save(e);
        d.assignId(saved.id);
    }

    private PointHistoryEntity toEntity(PointHistory d) {
        PointHistoryEntity e = new PointHistoryEntity();
        e.id = d.getId();
        e.userId = d.getUserId();
        e.pointBefore = d.getPointBefore();
        e.amount = d.getAmount();
        e.pointAfter = d.getPointAfter();
        e.transactionType = d.getTransactionType();
        return e;
    }
}
