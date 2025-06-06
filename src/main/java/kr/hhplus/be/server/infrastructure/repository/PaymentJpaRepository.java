package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.model.Payment;
import kr.hhplus.be.server.domain.repository.PaymentRepository;
import kr.hhplus.be.server.infrastructure.persistence.PaymentEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentJpaRepository implements PaymentRepository {
    private final SpringPaymentJpa jpa;

    public PaymentJpaRepository(SpringPaymentJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Payment save(Payment p) {
        PaymentEntity e = toEntity(p);
        PaymentEntity saved = jpa.save(e);

        p.assignId(saved.id);
        return p;
    }

    private Payment toDomain(PaymentEntity e) {
        return Payment.reconstitute(
                e.id,
                e.userId,
                e.amount,
                e.paymentMethod
        );
    }

    private PaymentEntity toEntity(Payment p) {
        PaymentEntity e = new PaymentEntity();
        e.id = p.getId();
        e.userId = p.getUserId();
        e.amount = p.getAmount();
        e.paymentMethod = p.getPaymentMethod();
        return e;
    }
}
