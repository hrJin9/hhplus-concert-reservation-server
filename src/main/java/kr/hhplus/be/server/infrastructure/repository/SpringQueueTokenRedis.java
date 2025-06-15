package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.infrastructure.persistence.QueueTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringQueueTokenRedis extends CrudRepository<QueueTokenEntity, UUID> {
    Optional<QueueToken> findByUserId(Long userId);
}
