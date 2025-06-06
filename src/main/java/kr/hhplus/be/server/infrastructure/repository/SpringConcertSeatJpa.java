package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.infrastructure.persistence.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringConcertSeatJpa extends JpaRepository<ConcertSeatEntity, Long> {
}
