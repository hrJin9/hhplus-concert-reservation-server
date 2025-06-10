package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.PaymentMethod;
import kr.hhplus.be.server.common.enums.PaymentStatus;

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
    @Enumerated(EnumType.STRING)
    public PaymentStatus paymentStatus;

}
