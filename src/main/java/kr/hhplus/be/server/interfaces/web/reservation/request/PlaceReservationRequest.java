package kr.hhplus.be.server.interfaces.web.reservation.request;

import kr.hhplus.be.server.application.reservation.dto.PlaceReservationCommand;

public record PlaceReservationRequest(
        Long concertSeatId
) {
    public PlaceReservationCommand toCommand() {
        return new PlaceReservationCommand(
                concertSeatId
        );
    }
}
