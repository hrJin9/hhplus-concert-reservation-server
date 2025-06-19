package kr.hhplus.be.server.domain.reservation.model;


import kr.hhplus.be.server.common.enums.ReservationStatus;

public class Reservation {
    private Long id;
    private final Long userId;
    private final Long concertSeatId;
    private  ReservationStatus status;

    public Reservation(Long id, Long userId, Long concertSeatId, ReservationStatus status) {
        this.id = id;
        this.userId = userId;
        this.concertSeatId = concertSeatId;
        this.status = status;
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

    public ReservationStatus getStatus() {
        return status;
    }

    public static Reservation create(Long userId, Long concertSeatId) {
        return new Reservation(
                null,
                userId,
                concertSeatId,
                ReservationStatus.AVAILABLE
        );
    }

    public static Reservation reconstitute(Long id, Long userId, Long concertSeatId, ReservationStatus status) {
        return new Reservation(
                id,
                userId,
                concertSeatId,
                status
        );
    }

    public void hold() {
        this.status = ReservationStatus.HOLD;
    }

    public void complete() {
        this.status = ReservationStatus.COMPLETED;
    }

    public void assignId(Long id) {
        this.id = id;
    }

}
