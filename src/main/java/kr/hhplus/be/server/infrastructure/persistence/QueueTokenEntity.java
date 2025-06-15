package kr.hhplus.be.server.infrastructure.persistence;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.hhplus.be.server.common.enums.TokenType;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@RedisHash("queue_token")
public class QueueTokenEntity {
    @Id
    public UUID id;
    public Long userId;
    public Long concertId;
    public Long issuedAt;
    public Long expiresAt;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType;
    @TimeToLive
    public Long ttlInSeconds;
}
