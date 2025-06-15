package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.TransactionType;

@Entity
@Table(name = "point_history")
public class PointHistoryEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long userId;
    public Long pointBefore;
    public Long amount;
    public Long pointAfter;
    public TransactionType transactionType;
}
