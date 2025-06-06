package kr.hhplus.be.server.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("queue_token")
public class QueueTokenEntity {
    @Id
    public Long userId;
    public Long issuedAt;
    public Long expiresAt;
    @TimeToLive
    public Long ttlInSeconds;
}
