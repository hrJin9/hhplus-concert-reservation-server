package kr.hhplus.be.server.application.concert.dto;

import kr.hhplus.be.server.domain.model.Concert;

import java.time.LocalDateTime;

public record ConcertInfo(
        Long concertId,
        String name,
        String artist,
        LocalDateTime date
) {
    public static ConcertInfo from(Concert concert) {
        return new ConcertInfo(
                concert.getId(),
                concert.getName(),
                concert.getArtist(),
                concert.getDate()
        );
    }

}
