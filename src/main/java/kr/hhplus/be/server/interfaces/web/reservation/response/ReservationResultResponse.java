package kr.hhplus.be.server.interfaces.web.reservation.response;

import kr.hhplus.be.server.application.reservation.dto.PlaceReservationResult;

public record ReservationResultResponse(
        Long reservationId
) {
    public static ReservationResultResponse from(PlaceReservationResult result) {
        return new ReservationResultResponse(
                result.reservationId()
        );
    }
}
