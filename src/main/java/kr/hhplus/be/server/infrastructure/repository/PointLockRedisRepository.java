package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.point.repository.PointLockRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class PointLockRedisRepository implements PointLockRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public PointLockRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean acquire(Long userId) {
        String key = "lock:point:userId" + userId;
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, userId, Duration.ofMinutes(5)); // TODO: 추후 properties에서 주입받기

        return Boolean.TRUE.equals(success);
    }

    @Override
    public void release(Long userId) {
        String key = "lock:point:userId" + userId;
        redisTemplate.delete(key);
    }
}
