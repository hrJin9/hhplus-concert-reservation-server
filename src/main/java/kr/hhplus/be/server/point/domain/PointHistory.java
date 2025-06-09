package kr.hhplus.be.server.point.domain;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.TransactionType;
import kr.hhplus.be.server.infrastructure.persistence.AuditableEntity;
import lombok.Getter;

@Entity
@Table(name = "point_history")
@Getter
public class PointHistory extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Point point;
    private Long userId;

    private Long pointBefore;

    private Long amount;

    private Long pointAfter;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    protected PointHistory(Long userId, Long pointBefore, Long amount, Long pointAfter, TransactionType transactionType) {
        this.userId = userId;
        this.pointBefore = pointBefore;
        this.amount = amount;
        this.pointAfter = pointAfter;
        this.transactionType = transactionType;
    }

    public static PointHistory charge(Long userId, Long beforePoint, Long amount, Long point) {
        return new PointHistory(
                userId,
                beforePoint,
                amount,
                point,
                TransactionType.CHARGE
        );
    }

    public static PointHistory use(Long userId, Long beforePoint, Long amount, Long point) {
        return new PointHistory(
                userId,
                beforePoint,
                amount,
                point,
                TransactionType.USE
        );
    }
}
