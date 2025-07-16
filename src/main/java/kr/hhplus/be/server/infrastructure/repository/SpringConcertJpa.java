package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.infrastructure.persistence.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringConcertJpa extends JpaRepository<ConcertEntity, Long> {

    @Query("SELECT c " +
            "FROM ConcertEntity c " +
            "WHERE c.date >= :currentTime " +
            "ORDER BY c.date DESC")
    List<ConcertEntity> findAvailableConcerts(LocalDateTime currentTime);
}
