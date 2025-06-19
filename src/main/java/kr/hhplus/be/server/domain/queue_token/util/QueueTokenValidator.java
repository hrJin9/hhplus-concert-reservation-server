package kr.hhplus.be.server.domain.queue_token.util;

import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.UUID;

public class QueueTokenValidator {
    private final QueueTokenRepository queueTokenRepository;

    public QueueTokenValidator(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }

    public QueueToken validate(String tokenId) {
        QueueToken token = queueTokenRepository.findByTokenId(tokenId);

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ApiException(ErrorCode.QUEUE_TOKEN_EXPIRED);
        }

        return token;
    }
}
