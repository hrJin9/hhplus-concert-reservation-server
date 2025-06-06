package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.infrastructure.persistence.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringReservationJpa extends JpaRepository<ReservationEntity, Long> {
}
