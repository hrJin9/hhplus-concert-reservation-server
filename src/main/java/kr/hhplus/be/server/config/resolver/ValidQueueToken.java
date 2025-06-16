package kr.hhplus.be.server.config.resolver;

public record ValidQueueToken(
        Long userId,
        Long issuedAt,
        Long expiresAt
) {
}
