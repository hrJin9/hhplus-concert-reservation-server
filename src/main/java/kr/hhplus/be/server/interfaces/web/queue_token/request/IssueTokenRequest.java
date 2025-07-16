package kr.hhplus.be.server.interfaces.web.queue_token.request;

import kr.hhplus.be.server.application.queue_token.dto.IssueTokenCommand;

public record IssueTokenRequest(
        Long userId
) {

    public IssueTokenCommand toCommand() {
        return new IssueTokenCommand(
                userId
        );
    }
}
