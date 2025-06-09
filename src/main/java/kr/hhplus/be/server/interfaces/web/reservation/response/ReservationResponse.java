package kr.hhplus.be.server.interfaces.web.reservation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.hhplus.be.server.application.reservation.dto.ReservationResult;
import kr.hhplus.be.server.common.enums.ReservationStatus;

public record ReservationResponse(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long reservationId,
        ReservationStatus reservationStatus
) {
    public static ReservationResponse from(ReservationResult reservationResult) {
        return new ReservationResponse(
                reservationResult.reservationId(),
                reservationResult.reservationStatus()
        );
    }
}
