package kr.hhplus.be.server.domain.reservation.repository;

import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.domain.reservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Reservation findById(Long reservationId);

    List<Reservation> findAllExpired();

    void saveAll(List<Reservation> expiredReservations);
}
