package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.queue_token.repository.QueueLockRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class QueueLockRedisRepository implements QueueLockRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String QUEUE_LOCK_KEY = "queue:lock";
    private static final Integer QUEUE_LOCK_MIN = 5;

    public QueueLockRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryLock() {
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(QUEUE_LOCK_KEY, "LOCK", Duration.ofSeconds(QUEUE_LOCK_MIN));
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void release() {
        redisTemplate.delete(QUEUE_LOCK_KEY);
    }
}
