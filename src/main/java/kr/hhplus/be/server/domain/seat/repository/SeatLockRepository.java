package kr.hhplus.be.server.domain.seat.repository;

public interface SeatLockRepository {
    boolean acquire(Long seatId, Long userId);
    void release(Long seatId, Long userId);
}
