package kr.hhplus.be.server.domain.seat.repository;

import kr.hhplus.be.server.common.enums.SeatStatus;
import kr.hhplus.be.server.domain.seat.model.Seat;

import java.util.List;

public interface SeatRepository {
    Seat findById(Long id);

    Seat save(Seat seat);

    List<Seat> findAllBySeatStatus(SeatStatus seatStatus);

    void saveAll(List<Seat> seats);
}
