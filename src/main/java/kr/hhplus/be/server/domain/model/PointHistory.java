package kr.hhplus.be.server.domain.model;

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

    private Long amount;

    private Long pointAfter;

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

    public static PointHistory createChargeHistory(Long userId, Long beforePoint, Long amount, Long point) {
        return new PointHistory(
                null,
                userId,
                beforePoint,
                amount,
                point,
                TransactionType.CHARGE
        );
    }

    public static PointHistory createUseHistory(Long id, Long userId, Long beforePoint, Long amount, Long point) {
        return new PointHistory(
                id,
                userId,
                beforePoint,
                amount,
                point,
                TransactionType.USE
        );
    }
}
