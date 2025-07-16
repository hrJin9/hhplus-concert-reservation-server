package kr.hhplus.be.server.infrastructure.repository;


import kr.hhplus.be.server.common.enums.SeatStatus;
import kr.hhplus.be.server.exception.ConcertSeatNotFoundException;
import kr.hhplus.be.server.domain.seat.model.Seat;
import kr.hhplus.be.server.domain.seat.repository.SeatRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.infrastructure.persistence.SeatEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatJpaRepository implements SeatRepository {
    private final SpringSeatJpa jpa;

    public SeatJpaRepository(SpringSeatJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Seat findById(Long id) {
        return jpa.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> new ConcertSeatNotFoundException(ErrorCode.SEAT_NOT_FOUND));
    }

    @Override
    public Seat save(Seat c) {
        SeatEntity e = toEntity(c);
        SeatEntity saved = jpa.save(e);

        c.assignId(saved.id);
        return c;
    }

    @Override
    public List<Seat> findAllBySeatStatus(SeatStatus seatStatus) {
        List<SeatEntity> seats =  jpa.findAllBySeatStatus(seatStatus);
        return seats.stream().map(this::toDomain).toList();
    }

    @Override
    public void saveAll(List<Seat> seats) {
        List<SeatEntity> seatEntities = seats.stream().map(this::toEntity).toList();
        jpa.saveAll(seatEntities);
    }

    private Seat toDomain(SeatEntity e) {
        return Seat.reconstitute(
                e.id,
                e.concertId,
                e.price,
                e.seatStatus,
                e.assignedUserId
        );
    }

    private SeatEntity toEntity(Seat c) {
        SeatEntity e = new SeatEntity();
        e.id = c.getId();
        e.concertId = c.getConcertId();
        e.price = c.getPrice();
        e.seatStatus = c.getSeatStatus();
        e.assignedUserId = c.getAssignedUserId();
        return e;
    }
}
