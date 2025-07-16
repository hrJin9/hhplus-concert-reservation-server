package kr.hhplus.be.server.interfaces.web.queue_token.response;

import kr.hhplus.be.server.application.queue_token.dto.QueueStatusResult;

public record QueueStatusResponse(
        Long userId,
        Integer position
) {
    public static QueueStatusResponse from(QueueStatusResult status) {
        return new QueueStatusResponse(
                status.userId(),
                status.position()
        );
    }
}
