package kr.hhplus.be.server.application.queue_token.dto;

import kr.hhplus.be.server.common.enums.TokenType;
import kr.hhplus.be.server.domain.queue_token.model.QueueToken;

public record IssueTokenResult(
        Long userId,
        Long issuedAt,
        Long expiresAt,
        TokenType tokenType
) {
    public static IssueTokenResult from(QueueToken token) {
        return new IssueTokenResult(
                token.getUserId(),
                token.getIssuedAt(),
                token.getExpiresAt(),
                token.getTokenType()
        );
    }
}
