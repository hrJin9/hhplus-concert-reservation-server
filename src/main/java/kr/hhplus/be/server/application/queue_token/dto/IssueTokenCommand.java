package kr.hhplus.be.server.application.queue_token.dto;

import kr.hhplus.be.server.domain.queue_token.model.QueueToken;

public record IssueTokenCommand(
        Long userId,
        Long concertId
) {
    public static IssueTokenCommand from(QueueToken token) {
        return new IssueTokenCommand(
                token.getUserId(),
                token.getConcertId()
        );
    }
}
