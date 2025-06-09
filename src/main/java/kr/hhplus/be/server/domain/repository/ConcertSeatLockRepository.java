package kr.hhplus.be.server.domain.repository;

public interface ConcertSeatLockRepository {
    boolean acquire(Long concertSeatId, Long userId);
    void release(Long concertSeatId, Long userId);
}
