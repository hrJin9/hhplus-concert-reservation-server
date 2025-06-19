package kr.hhplus.be.server.interfaces.web.queue_token.response;

import kr.hhplus.be.server.application.queue_token.dto.IssueTokenResult;
import kr.hhplus.be.server.common.enums.QueueStatus;

import java.time.LocalDateTime;

public record IssueTokenResultResponse(
        Long userId,
        QueueStatus queueStatus,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt
) {
    public static IssueTokenResultResponse from(IssueTokenResult result) {
        return new IssueTokenResultResponse(
                result.userId(),
                result.queueStatus(),
                result.issuedAt(),
                result.expiresAt()
        );
    }
}
