package kr.hhplus.be.server.domain.queue_token.util;

import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;

import java.util.UUID;

public class QueueTokenValidator {
    private final QueueTokenRepository queueTokenRepository;

    public QueueTokenValidator(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }

    public QueueToken validate(UUID tokenId) {
        return queueTokenRepository.findByTokenId(tokenId);
    }
}
