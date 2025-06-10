package kr.hhplus.be.server.application.reservation.dto;

import kr.hhplus.be.server.domain.model.Reservation;

public record PlaceReservationResult(
        Long reservationId
) {
    public static PlaceReservationResult from(Reservation reservation) {
        return new PlaceReservationResult(
                reservation.getId()
        );
    }
}
