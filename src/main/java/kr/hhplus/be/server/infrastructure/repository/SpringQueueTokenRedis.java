package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.infrastructure.persistence.QueueTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface SpringQueueTokenRedis extends CrudRepository<QueueTokenEntity, Long> {
}
