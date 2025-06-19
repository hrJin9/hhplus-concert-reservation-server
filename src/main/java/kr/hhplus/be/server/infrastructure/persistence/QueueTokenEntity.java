package kr.hhplus.be.server.infrastructure.persistence;

import kr.hhplus.be.server.common.enums.QueueStatus;

import java.time.LocalDateTime;

public class QueueTokenEntity {
    public String id;
    public Long userId;
    public QueueStatus queueStatus;
    public LocalDateTime issuedAt;
    public LocalDateTime expiresAt;
    public Integer ttlInSeconds;

    public QueueTokenEntity() {
    }
}
