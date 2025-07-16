package kr.hhplus.be.server.application.reservation.dto;

import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.domain.reservation.model.Reservation;

public record PlaceReservationResult(
        Long reservationId,
        ReservationStatus reservationStatus
) {
    public static PlaceReservationResult from(Reservation reservation) {
        return new PlaceReservationResult(
                reservation.getId(),
                reservation.getStatus()
        );
    }

}
