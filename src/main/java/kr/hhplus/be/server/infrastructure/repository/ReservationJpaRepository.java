package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.reservation.model.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.infrastructure.persistence.ReservationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationJpaRepository implements ReservationRepository {
    private final SpringReservationJpa jpa;

    public ReservationJpaRepository(SpringReservationJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Reservation save(Reservation r) {
        ReservationEntity e = toEntity(r);
        ReservationEntity saved = jpa.save(e);

        r.assignId(saved.id);
        return r;
    }

    private Reservation toDomain(ReservationEntity e) {
        return Reservation.reconstitute(
                e.id,
                e.userId,
                e.concertSeatId
        );
    }

    private ReservationEntity toEntity(Reservation r) {
        ReservationEntity e = new ReservationEntity();
        e.id = r.getId();
        e.userId = r.getUserId();
        e.concertSeatId = r.getConcertSeatId();
        return e;
    }


}
