package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.infrastructure.persistence.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringConcertJpa extends JpaRepository<ConcertEntity, Long> {
}
