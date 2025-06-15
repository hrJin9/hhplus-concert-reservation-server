package kr.hhplus.be.server.application.queue_token.dto;

public record QueueStatus(
        Long userId,
        Long issuedAt,
        Long expiresAt,
        Long estimatedWaitSeconds
) {
}
