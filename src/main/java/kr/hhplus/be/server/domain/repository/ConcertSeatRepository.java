package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.domain.model.ConcertSeat;

import java.util.Optional;

public interface ConcertSeatRepository {
    Optional<ConcertSeat> findById(Long id);

    ConcertSeat save(ConcertSeat concertSeat);

    void update(ConcertSeat concertSeat);
}
