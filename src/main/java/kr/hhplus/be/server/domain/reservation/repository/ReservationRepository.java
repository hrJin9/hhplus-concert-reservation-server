package kr.hhplus.be.server.domain.reservation.repository;

import kr.hhplus.be.server.domain.reservation.model.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
}
