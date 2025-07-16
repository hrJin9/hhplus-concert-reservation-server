package kr.hhplus.be.server.domain.reservation.model;


import kr.hhplus.be.server.common.enums.ReservationStatus;

public class Reservation {
    private Long id;
    private final Long userId;
    private final Long seatId;
    private  ReservationStatus status;

    public Reservation(Long id, Long userId, Long seatId, ReservationStatus status) {
        this.id = id;
        this.userId = userId;
        this.seatId = seatId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public static Reservation create(Long userId, Long seatId) {
        return new Reservation(
                null,
                userId,
                seatId,
                ReservationStatus.AVAILABLE
        );
    }

    public static Reservation reconstitute(Long id, Long userId, Long seatId, ReservationStatus status) {
        return new Reservation(
                id,
                userId,
                seatId,
                status
        );
    }

    public void hold() {
        this.status = ReservationStatus.HOLD;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELD;
    }

    public void complete() {
        this.status = ReservationStatus.COMPLETED;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public void expire() {
        this.status = ReservationStatus.EXPIRED;
    }

    public boolean isCompleted() {
        return this.status.equals(ReservationStatus.COMPLETED);
    }
}
