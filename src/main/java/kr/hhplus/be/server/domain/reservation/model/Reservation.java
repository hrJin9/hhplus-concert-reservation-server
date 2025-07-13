package kr.hhplus.be.server.domain.reservation.model;


import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;

public class Reservation {
    private Long id;
    private final Long userId;
    private final Long seatId;
    private  ReservationStatus status;
    private static final Integer timeOutMin = 5;

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

    public void hold(Long userId) {
        if(!this.userId.equals(userId)) {
            throw new ApiException(ErrorCode.RESERVATION_USER_NOT_MATCH);
        }

        if(this.status.equals(ReservationStatus.CANCELD)) {
            throw new ApiException(ErrorCode.RESERVATION_CANCELD);
        }

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

    public void enable() {
        this.status = ReservationStatus.AVAILABLE;
    }

    public void expire() {
        this.status = ReservationStatus.EXPIRED;
    }
}
