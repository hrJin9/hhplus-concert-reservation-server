package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.domain.reservation.model.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.exception.ReservationNotFoundException;
import kr.hhplus.be.server.infrastructure.persistence.ReservationEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationJpaRepository implements ReservationRepository {
    private final SpringReservationJpa jpa;

    public ReservationJpaRepository(SpringReservationJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Reservation findById(Long id) {
        return jpa.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> new ReservationNotFoundException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    @Override
    public Reservation save(Reservation r) {
        ReservationEntity e = toEntity(r);
        ReservationEntity saved = jpa.save(e);

        r.assignId(saved.id);
        return r;
    }

    @Override
    public List<Reservation> findAllExpired() {
        LocalDateTime timeoutThreshold = LocalDateTime.now().minusMinutes(5);
        List<ReservationEntity> entities = jpa.findAllByStatusAndReservedAtBefore(
                ReservationStatus.HOLD,
                timeoutThreshold
        );

        return entities.stream().map(this::toDomain).toList();
    }

    @Override
    public void saveAll(List<Reservation> expiredReservations) {
        List<ReservationEntity> entities = expiredReservations.stream().map(this::toEntity).toList();
        jpa.saveAll(entities);
    }

    private Reservation toDomain(ReservationEntity e) {
        return Reservation.reconstitute(
                e.id,
                e.userId,
                e.concertSeatId,
                e.status
        );
    }

    private ReservationEntity toEntity(Reservation r) {
        ReservationEntity e = new ReservationEntity();
        e.id = r.getId();
        e.userId = r.getUserId();
        e.concertSeatId = r.getSeatId();
        e.status = r.getStatus();
        return e;
    }


}
