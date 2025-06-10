package kr.hhplus.be.server.infrastructure.repository;


import kr.hhplus.be.server.domain.exception.ConcertSeatNotFoundException;
import kr.hhplus.be.server.domain.model.ConcertSeat;
import kr.hhplus.be.server.domain.repository.ConcertSeatRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.infrastructure.persistence.ConcertSeatEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConcertSeatJpaRepository implements ConcertSeatRepository {
    private final SpringConcertSeatJpa jpa;

    public ConcertSeatJpaRepository(SpringConcertSeatJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public ConcertSeat findById(Long id) {
        return jpa.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> new ConcertSeatNotFoundException(ErrorCode.SEAT_NOT_FOUND));
    }

    @Override
    public ConcertSeat save(ConcertSeat c) {
        ConcertSeatEntity e = toEntity(c);
        ConcertSeatEntity saved = jpa.save(e);

        c.assignId(saved.id);
        return c;
    }

    @Override
    public void update(ConcertSeat concertSeat) {
        ConcertSeatEntity e = jpa.findById(concertSeat.getId())
                .orElseThrow(() -> new ConcertSeatNotFoundException(ErrorCode.SEAT_NOT_FOUND));

        e.seatStatus = concertSeat.getSeatStatus();
        e.price = concertSeat.getPrice();
    }

    private ConcertSeat toDomain(ConcertSeatEntity e) {
        return ConcertSeat.reconstitute(
                e.id,
                e.concertId,
                e.seatId,
                e.seatStatus,
                e.price
        );
    }

    private ConcertSeatEntity toEntity(ConcertSeat c) {
        ConcertSeatEntity e = new ConcertSeatEntity();
        e.id = c.getId();
        e.concertId = c.getConcertId();
        e.seatId = c.getSeatId();
        e.seatStatus = c.getSeatStatus();
        e.price = c.getPrice();
        return e;
    }
}
