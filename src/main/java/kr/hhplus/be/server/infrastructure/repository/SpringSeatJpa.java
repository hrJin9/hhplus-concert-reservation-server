package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.common.enums.SeatStatus;
import kr.hhplus.be.server.infrastructure.persistence.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringSeatJpa extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findAllBySeatStatus(SeatStatus seatStatus);
}
