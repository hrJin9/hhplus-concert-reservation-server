package kr.hhplus.be.server.point.service.dto;

public record UsePointCommand(
        Long userId,
        Long amount
) {
}
