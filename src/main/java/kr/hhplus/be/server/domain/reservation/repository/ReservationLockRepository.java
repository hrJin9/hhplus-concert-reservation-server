package kr.hhplus.be.server.domain.reservation.repository;

public interface ReservationLockRepository {
    boolean acquire(Long reservationId, Long userId);
    void release(Long reservationId, Long userId);
}
