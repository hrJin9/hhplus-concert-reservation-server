package kr.hhplus.be.server.interfaces.web.reservation.request;

import kr.hhplus.be.server.application.reservation.dto.CreateReservationCommand;

public record CreateReservationRequest(
        Long concertSeatId
) {
    public CreateReservationCommand toCommand() {
        return new CreateReservationCommand(
                concertSeatId
        );
    }
}
