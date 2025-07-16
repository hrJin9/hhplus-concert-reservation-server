package kr.hhplus.be.server.interfaces.web.concert.response;

import kr.hhplus.be.server.application.concert.dto.ConcertInfo;

import java.time.LocalDateTime;

public record ConcertInfoResponse(
        Long concertId,
        String name,
        String artist,
        LocalDateTime date
) {
    public static ConcertInfoResponse from(ConcertInfo result) {
        return new ConcertInfoResponse(
                result.concertId(),
                result.name(),
                result.artist(),
                result.date()
        );
    }
}
