package kr.hhplus.be.server.interfaces.web.queue_token.resolver;

public record ValidQueueToken(
        Long userId,
        Long issuedAt,
        Long expiresAt
) {
}
