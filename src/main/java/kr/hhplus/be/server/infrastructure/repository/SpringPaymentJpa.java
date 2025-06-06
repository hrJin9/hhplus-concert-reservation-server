package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.infrastructure.persistence.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPaymentJpa extends JpaRepository<PaymentEntity, Long> {
}
