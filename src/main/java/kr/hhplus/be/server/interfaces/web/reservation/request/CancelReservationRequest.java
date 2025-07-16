package kr.hhplus.be.server.interfaces.web.reservation.request;

import jakarta.validation.constraints.NotNull;

public record CancelReservationRequest(
        @NotNull
        Long reservationId
) {
}
