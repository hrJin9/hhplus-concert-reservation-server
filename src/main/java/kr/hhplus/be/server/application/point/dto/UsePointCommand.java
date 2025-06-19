package kr.hhplus.be.server.application.point.dto;

public record UsePointCommand(
        Long reservationId,
        Long amount
) {
}
