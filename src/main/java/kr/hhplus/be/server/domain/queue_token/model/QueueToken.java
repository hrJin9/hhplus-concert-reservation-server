package kr.hhplus.be.server.domain.queue_token.model;

import kr.hhplus.be.server.common.enums.QueueStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class QueueToken {
    private final String id;
    private final Long userId;
    private QueueStatus queueStatus;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiresAt;

    // TODO : 추후 @Value로 주입필요 ?
    public static final Integer TOKEN_EXPIRE_MIN = 5;
    public static final Integer MAX_ACTIVABLE_USER = 100;
    public static final Integer WAITING_TIME_PER_USER = 10;

    private QueueToken(String id, Long userId, QueueStatus queueStatus, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.id = id;
        this.userId = userId;
        this.queueStatus = queueStatus;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public QueueStatus getQueueStatus() {
        return queueStatus;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }


    public static QueueToken createActiveToken(Long userId) {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ttl = now.plusMinutes(TOKEN_EXPIRE_MIN);

        return new QueueToken(
                id,
                userId,
                QueueStatus.ACTIVE,
                now,
                ttl
        );
    }

    public static QueueToken createWaitingToken(Long userId) {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ttl = now.plusMinutes(TOKEN_EXPIRE_MIN);

        return new QueueToken(
                id,
                userId,
                QueueStatus.WAITING,
                now,
                ttl
        );
    }

    public static QueueToken of(String id, Long userId, QueueStatus queueStatus, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        return new QueueToken(
                id,
                userId,
                queueStatus,
                issuedAt,
                expiresAt
        );
    }

    public Integer getTokenExpireMin() {
        return TOKEN_EXPIRE_MIN;
    }
}