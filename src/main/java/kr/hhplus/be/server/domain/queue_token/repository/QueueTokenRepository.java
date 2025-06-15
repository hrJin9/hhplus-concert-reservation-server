package kr.hhplus.be.server.domain.queue_token.repository;

import kr.hhplus.be.server.domain.queue_token.model.QueueToken;

import java.util.Optional;
import java.util.UUID;

public interface QueueTokenRepository {
    QueueToken findByTokenId(UUID tokenId);

    Optional<QueueToken> findByUserId(Long userId);

    QueueToken save(QueueToken token);

}
