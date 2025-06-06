package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.PaymentMethod;

@Entity
@Table(name = "payment")
public class PaymentEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long userId;
    public Long amount;
    @Enumerated(EnumType.STRING)
    public PaymentMethod paymentMethod;

}
