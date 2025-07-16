package kr.hhplus.be.server.interfaces.web.reservation.response;

import kr.hhplus.be.server.common.enums.ReservationStatus;

public record ReservationResultResponse(
        Long reservationId,
        ReservationStatus reservationStatus
) {
    public static ReservationResultResponse of(Long reservationId, ReservationStatus reservationStatus) {
        return new ReservationResultResponse(
                reservationId,
                reservationStatus
        );
    }
}
