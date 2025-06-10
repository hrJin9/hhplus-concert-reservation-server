package kr.hhplus.be.server.domain.model;

import kr.hhplus.be.server.common.enums.SeatStatus;
import kr.hhplus.be.server.domain.exception.NotAvailableSeatException;
import kr.hhplus.be.server.exception.ErrorCode;

public class ConcertSeat {
    private Long id;
    private final Long concertId;
    private final Long seatId;
    private SeatStatus seatStatus;
    private final Long price;

    private ConcertSeat(Long id, Long concertId, Long seatId, SeatStatus seatStatus, Long price) {
        this.id = id;
        this.concertId = concertId;
        this.seatId = seatId;
        this.seatStatus = seatStatus;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Long getConcertId() {
        return concertId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public Long getPrice() {
        return price;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public static ConcertSeat create(Long concertId, Long seatId, Long price) {
        return new ConcertSeat(
                null,
                concertId,
                seatId,
                SeatStatus.AVAILABLE,
                price
        );
    }

    public static ConcertSeat reconstitute(Long id, Long concertId, Long seatId, SeatStatus seatStatus, Long price) {
        return new ConcertSeat(
                id,
                concertId,
                seatId,
                seatStatus,
                price
        );
    }

    public void hold() {
        if(this.seatStatus != SeatStatus.AVAILABLE) {
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

    public void reserved() {
        this.seatStatus = SeatStatus.RESERVED;
    }
}
