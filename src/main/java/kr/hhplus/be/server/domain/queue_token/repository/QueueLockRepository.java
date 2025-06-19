package kr.hhplus.be.server.domain.queue_token.repository;

public interface QueueLockRepository {
    boolean tryLock();
    void release();
}
