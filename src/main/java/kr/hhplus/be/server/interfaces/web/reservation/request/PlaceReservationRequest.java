package kr.hhplus.be.server.interfaces.web.reservation.request;

import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationCommand;

public record PlaceReservationRequest(
        @NotNull
        Long concertId,
        @NotNull
        Long seatId
) {
    public PlaceReservationCommand toCommand() {
        return new PlaceReservationCommand(
                concertId,
                seatId
        );
    }
}
