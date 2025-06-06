package kr.hhplus.be.server.domain.model;

import java.time.Duration;

public class QueueToken {
    private final Long userId;
    private final Long issuedAt;
    private final Long expiresAt;

    public QueueToken(Long userId, Long issuedAt, Long expiresAt) {
        this.userId = userId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getIssuedAt() {
        return issuedAt;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public static QueueToken create(Long userId) {
        long now = System.currentTimeMillis();
        long ttl = Duration.ofMinutes(5).toMillis();

        return new QueueToken(
                userId,
                now,
                now + ttl
        );
    }

    public static QueueToken reconstitute(Long userId, Long issuedAt, Long expiresAt) {
        return new QueueToken(
                userId,
                issuedAt,
                expiresAt
        );
    }

}