package kr.hhplus.be.server.interfaces.web.queue_token.response;

import kr.hhplus.be.server.application.queue_token.dto.QueueStatus;

import java.time.Instant;

public record QueueStatusResponse(
        Long userId,
        Long issuedAt,
        Long expiresAt,
        Long estimatedWaitSeconds
) {
    public static QueueStatusResponse from(QueueStatus status) {
        return new QueueStatusResponse(
                status.userId(),
                status.issuedAt(),
                status.expiresAt(),
                status.estimatedWaitSeconds()
        );
    }
}
