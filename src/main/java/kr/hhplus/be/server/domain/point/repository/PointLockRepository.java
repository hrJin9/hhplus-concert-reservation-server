package kr.hhplus.be.server.domain.point.repository;

public interface PointLockRepository {
    boolean acquire(Long userId);
    void release(Long userId);
}
