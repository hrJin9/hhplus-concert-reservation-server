package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.repository.ConcertSeatLockRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class ConcertSeatLockRedisRepository implements ConcertSeatLockRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public ConcertSeatLockRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean acquire(Long concertSeatId, Long userId) {
        String key = "lock:seat:" + concertSeatId;
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, userId, Duration.ofMinutes(5)); // TODO: 추후 properties에서 주입받기

        return Boolean.TRUE.equals(success);
    }

    @Override
    public void release(Long concertSeatId, Long userId) {
        String key = "lock:seat:" + concertSeatId;
        Long owner = (Long) redisTemplate.opsForValue()
                .get(key);

        if (owner.equals(userId)) {
            redisTemplate.delete(key);
        }
    }
}
