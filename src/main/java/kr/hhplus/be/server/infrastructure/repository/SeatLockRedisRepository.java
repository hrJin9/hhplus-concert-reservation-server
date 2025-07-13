package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.seat.repository.SeatLockRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class SeatLockRedisRepository implements SeatLockRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public SeatLockRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean acquire(Long seatId, Long userId) {
        String key = "lock:seat:" + seatId;
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, userId, Duration.ofMinutes(5)); // TODO: 추후 properties에서 주입받기

        return Boolean.TRUE.equals(success);
    }

    @Override
    public void release(Long seatId, Long userId) {
        String key = "lock:seat:" + seatId;
        Long owner = (Long) redisTemplate.opsForValue()
                .get(key);

        if (owner.equals(userId)) {
            redisTemplate.delete(key);
        }
    }
}
