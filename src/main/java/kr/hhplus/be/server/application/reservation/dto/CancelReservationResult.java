package kr.hhplus.be.server.application.reservation.dto;

import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.common.enums.SeatStatus;

public record CancelReservationResult(
        Long reservationId,
        Long seatId,
        ReservationStatus reservationStatus,
        SeatStatus seatStatus
) {
    public static CancelReservationResult of(Long reservationId, Long seatId, ReservationStatus reservationStatus, SeatStatus seatStatus) {
        return new CancelReservationResult(
                reservationId,
                seatId,
                reservationStatus,
                seatStatus
        );
    }
}
