package kr.hhplus.be.server.application.queue_token.dto;

import kr.hhplus.be.server.common.enums.QueueStatus;
import kr.hhplus.be.server.domain.queue_token.model.QueueToken;

import java.time.LocalDateTime;

public record IssueTokenResult(
        Long userId,
        QueueStatus queueStatus,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt
) {
    public static IssueTokenResult from(QueueToken token) {
        return new IssueTokenResult(
                token.getUserId(),
                token.getQueueStatus(),
                token.getIssuedAt(),
                token.getExpiresAt()
        );
    }
}
