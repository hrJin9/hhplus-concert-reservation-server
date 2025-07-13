package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.reservation.repository.ReservationLockRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class ReservationLockRedisRepository implements ReservationLockRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public ReservationLockRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean acquire(Long reservationId, Long userId) {
        String key = "lock:reservation:" + reservationId;
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, userId, Duration.ofMinutes(5)); // TODO: 추후 properties에서 주입받기

        return Boolean.TRUE.equals(success);
    }

    @Override
    public void release(Long reservationId, Long userId) {
        String key = "lock:reservation:" + reservationId;
        Long owner = (Long) redisTemplate.opsForValue()
                .get(key);

        if (owner.equals(userId)) {
            redisTemplate.delete(key);
        }
    }
}
