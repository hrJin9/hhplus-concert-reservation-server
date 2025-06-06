package kr.hhplus.be.server.concert.web.response;

import kr.hhplus.be.server.concert.domain.Concert;

import java.time.LocalDateTime;

public record ConcertResponse (
        Long concertId,
        String name,
        String artist,
        LocalDateTime date
) {
    public static ConcertResponse from(Concert concert) {
        return new ConcertResponse(
                concert.getId(),
                concert.getName(),
                concert.getArtist(),
                concert.getDate()
        );
    }
}
