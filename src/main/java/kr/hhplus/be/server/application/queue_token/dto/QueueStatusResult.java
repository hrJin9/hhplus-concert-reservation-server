package kr.hhplus.be.server.application.queue_token.dto;

public record QueueStatusResult(
        Long userId,
        Integer position
) {
    public static QueueStatusResult of(Long userId, int position) {
        return new QueueStatusResult(
                userId,
                position
        );
    }
}
