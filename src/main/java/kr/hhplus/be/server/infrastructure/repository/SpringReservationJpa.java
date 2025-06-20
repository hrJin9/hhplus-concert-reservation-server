package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.infrastructure.persistence.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringReservationJpa extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findAllByStatusAndReservedAtBefore(ReservationStatus status, LocalDateTime beforeTime);
}
