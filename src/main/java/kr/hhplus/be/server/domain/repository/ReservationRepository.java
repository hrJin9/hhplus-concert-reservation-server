package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.domain.model.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
}
