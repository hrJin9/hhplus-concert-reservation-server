package kr.hhplus.be.server.application.seat.dto;

import kr.hhplus.be.server.common.enums.SeatStatus;

public record CancelSeatResult(
        Long seatId,
        SeatStatus seatStatus
) {
    public static CancelSeatResult of(Long seatId, SeatStatus seatStatus) {
        return new CancelSeatResult(
                seatId,
                seatStatus
        );
    }
}
