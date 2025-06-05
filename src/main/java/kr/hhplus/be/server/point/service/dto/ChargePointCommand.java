package kr.hhplus.be.server.point.service.dto;

public record ChargePointCommand(
        Long userId,
        Long amount
) {
}
