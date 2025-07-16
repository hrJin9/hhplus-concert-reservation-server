package kr.hhplus.be.server.config.resolver;

import kr.hhplus.be.server.common.enums.QueueStatus;

import java.time.LocalDateTime;

public record ValidQueueToken(
        String tokenId,
        Long userId,
        QueueStatus queueStatus,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt
) {
}
