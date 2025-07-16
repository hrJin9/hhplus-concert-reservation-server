package kr.hhplus.be.server.domain.seat.model;

import kr.hhplus.be.server.common.enums.SeatStatus;

public class Seat {
    private Long id;
    private final Long concertId;
    private final Long price;
    private SeatStatus seatStatus;

    public Seat(Long id, Long concertId, Long price, SeatStatus seatStatus) {
        this.id = id;
        this.concertId = concertId;
        this.price = price;
        this.seatStatus = seatStatus;
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


    public void assignId(Long id) {
        this.id = id;
    }

    public static Seat create(Long concertId, Long price) {
        return new Seat(
                null,
                concertId,
                price,
                SeatStatus.AVAILABLE
        );
    }

    public static Seat reconstitute(Long id, Long concertId, Long price, SeatStatus seatStatus) {
        return new Seat(
                id,
                concertId,
                price,
                seatStatus
        );
    }

    public boolean isAvailable() {
        return this.seatStatus.equals(SeatStatus.AVAILABLE);
    }

    public boolean isHold() {
        return this.seatStatus.equals(SeatStatus.HOLD);
    }

    public boolean isReserved() {
        return this.seatStatus.equals(SeatStatus.RESERVED);
    }

    public void hold() {
        this.seatStatus = SeatStatus.HOLD;
    }

    public void release() {
        this.seatStatus = SeatStatus.AVAILABLE;
    }

    public void reserve() {
        this.seatStatus = SeatStatus.RESERVED;
    }

    public void expire() {
        this.seatStatus = SeatStatus.EXPIRED;
    }

    public void cancel() {
        this.seatStatus = SeatStatus.CANCELED;
    }
}
