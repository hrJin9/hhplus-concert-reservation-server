package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.domain.model.QueueToken;

import java.time.Duration;
import java.util.Optional;

public interface QueueTokenRepository {
    Optional<QueueToken> findByTokenId(Long tokenId);

    void save(QueueToken token, Long ttlInSeconds);
}
