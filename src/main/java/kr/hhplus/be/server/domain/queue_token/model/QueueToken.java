package kr.hhplus.be.server.domain.queue_token.model;

import kr.hhplus.be.server.common.enums.TokenType;

import java.time.Duration;
import java.util.UUID;

public class QueueToken {
    private UUID id;
    private final Long userId;
    private final Long concertId;
    private final Long issuedAt;
    private final Long expiresAt;
    private final TokenType tokenType;

    private QueueToken(UUID id, Long userId, Long concertId, Long issuedAt, Long expiresAt, TokenType tokenType) {
        this.id = id;
        this.userId = userId;
        this.concertId = concertId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.tokenType = tokenType;
    }

    public UUID getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public Long getIssuedAt() {
        return issuedAt;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public static QueueToken create(Long userId, Long concertId) {
        UUID id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        long ttl = Duration.ofMinutes(5).toMillis();

        return new QueueToken(
                id,
                userId,
                concertId,
                now,
                now + ttl,
                TokenType.QUEUE
        );
    }

    public static QueueToken reconstitute(UUID id, Long userId, Long concertId, Long issuedAt, Long expiresAt, TokenType tokenType) {
        return new QueueToken(
                id,
                userId,
                concertId,
                issuedAt,
                expiresAt,
                tokenType
        );
    }
}