package kr.hhplus.be.server.domain.concertSeat.repository;

import kr.hhplus.be.server.domain.concertSeat.model.ConcertSeat;

public interface ConcertSeatRepository {
    ConcertSeat findById(Long id);

    ConcertSeat save(ConcertSeat concertSeat);

    void update(ConcertSeat concertSeat);
}
