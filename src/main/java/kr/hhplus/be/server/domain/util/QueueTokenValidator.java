package kr.hhplus.be.server.domain.util;

import kr.hhplus.be.server.domain.exception.QueueTokenExpiredException;
import kr.hhplus.be.server.domain.model.QueueToken;
import kr.hhplus.be.server.domain.repository.QueueTokenRepository;
import kr.hhplus.be.server.exception.ErrorCode;

public class QueueTokenValidator {
    private final QueueTokenRepository queueTokenRepository;

    public QueueTokenValidator(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }

    public QueueToken validate(Long tokenId) {
        return queueTokenRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new QueueTokenExpiredException(ErrorCode.QUEUE_TOKEN_EXPIRED));
    }
}
