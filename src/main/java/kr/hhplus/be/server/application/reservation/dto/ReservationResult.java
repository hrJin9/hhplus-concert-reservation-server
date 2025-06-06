package kr.hhplus.be.server.application.reservation.dto;

import kr.hhplus.be.server.common.enums.ReservationStatus;

public record ReservationResult(
        Long reservationId,
        ReservationStatus reservationStatus
) {
    public static ReservationResult success(Long reservationId, ReservationStatus reservationStatus) {
        return new ReservationResult(
                reservationId,
                reservationStatus
        );
    }
    public static ReservationResult failed(ReservationStatus reservationStatus) {
        return new ReservationResult(
                null,
                reservationStatus
        );
    }

}
