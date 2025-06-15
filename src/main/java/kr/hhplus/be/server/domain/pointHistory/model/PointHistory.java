package kr.hhplus.be.server.domain.pointHistory.model;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.TransactionType;
import kr.hhplus.be.server.infrastructure.persistence.AuditableEntity;

@Entity
@Table(name = "point_history")
public class PointHistory extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final Long userId;

    private final Long pointBefore;

    private final Long amount;

    private final Long pointAfter;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    protected PointHistory(Long id, Long userId, Long pointBefore, Long amount, Long pointAfter, TransactionType transactionType) {
        this.id = id;
        this.userId = userId;
        this.pointBefore = pointBefore;
        this.amount = amount;
        this.pointAfter = pointAfter;
        this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPointBefore() {
        return pointBefore;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getPointAfter() {
        return pointAfter;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public static PointHistory charge(Long userId, Long pointBefore, Long amount, Long pointAfter) {
        return new PointHistory(
                null,
                userId,
                pointBefore,
                amount,
                pointAfter,
                TransactionType.CHARGE
        );
    }

    public static PointHistory use(Long userId, Long pointBefore, Long amount, Long pointAfter) {
        return new PointHistory(
                null,
                userId,
                pointBefore,
                amount,
                pointAfter,
                TransactionType.USE
        );
    }

    public void assignId(Long id) {
        this.id = id;
    }
}
