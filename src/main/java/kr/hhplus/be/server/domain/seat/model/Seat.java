package kr.hhplus.be.server.domain.seat.model;

import kr.hhplus.be.server.common.enums.SeatStatus;
import kr.hhplus.be.server.exception.NotAvailableSeatException;
import kr.hhplus.be.server.exception.ErrorCode;

public class Seat {
    private Long id;
    private final Long concertId;
    private final Long price;
    private SeatStatus seatStatus;
    private Long assignedUserId;

    public Seat(Long id, Long concertId, Long price, SeatStatus seatStatus, Long assignedUserId) {
        this.id = id;
        this.concertId = concertId;
        this.price = price;
        this.seatStatus = seatStatus;
        this.assignedUserId = assignedUserId;
    }

    public Long getId() {
        return id;
    }

    public Long getConcertId() {
        return concertId;
    }

    public Long getPrice() {
        return price;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public static Seat create(Long concertId, Long price, Long userId) {
        return new Seat(
                null,
                concertId,
                price,
                SeatStatus.AVAILABLE,
                userId
        );
    }

    public static Seat reconstitute(Long id, Long concertId, Long price, SeatStatus seatStatus, Long assignedUserId) {
        return new Seat(
                id,
                concertId,
                price,
                seatStatus,
                assignedUserId
        );
    }

    public void hold() {
        if(!this.seatStatus.equals(SeatStatus.AVAILABLE)) {
            throw new NotAvailableSeatException(ErrorCode.SEAT_NOT_AVAILABLE);
        }
        this.seatStatus = SeatStatus.HOLD;
    }

    public void release() {
        this.seatStatus = SeatStatus.AVAILABLE;
    }

    public boolean isAvailable() {
        return this.seatStatus.equals(SeatStatus.AVAILABLE);
    }

    public void reserve() {
        this.seatStatus = SeatStatus.RESERVED;
    }

    public void assignAndHold(Long userId) {
        this.hold();
        this.assignedUserId = userId;
    }

    public void expire() {
        this.seatStatus = SeatStatus.EXPIRED;
    }
}
