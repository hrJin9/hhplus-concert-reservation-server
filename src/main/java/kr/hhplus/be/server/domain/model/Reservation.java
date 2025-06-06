package kr.hhplus.be.server.domain.model;

public class Reservation {
    private Long id;
    private final Long userId;
    private final Long concertSeatId;

    private Reservation(Long id, Long userId, Long concertSeatId) {
        this.id = id;
        this.userId = userId;
        this.concertSeatId = concertSeatId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getConcertSeatId() {
        return concertSeatId;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public static Reservation reconstitute(Long id, Long userId, Long concertSeatId) {
        return new Reservation(
                id,
                userId,
                concertSeatId
        );
    }

    public static Reservation create(Long userId, Long concertSeatId) {
        return new Reservation(
                null,
                userId,
                concertSeatId
        );
    }
}
