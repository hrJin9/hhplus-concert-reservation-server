package kr.hhplus.be.server.interfaces.web.queue_token.response;

import kr.hhplus.be.server.application.queue_token.dto.IssueTokenResult;
import kr.hhplus.be.server.application.queue_token.dto.QueueStatus;
import kr.hhplus.be.server.common.enums.TokenType;

import java.time.Instant;

public record IssueTokenResultResponse(
        Long userId,
        Long issuedAt,
        Long expiresAt,
        TokenType tokenType
) {
    public static IssueTokenResultResponse from(IssueTokenResult result) {
        return new IssueTokenResultResponse(
                result.userId(),
                result.issuedAt(),
                result.expiresAt(),
                result.tokenType()
        );
    }
}
