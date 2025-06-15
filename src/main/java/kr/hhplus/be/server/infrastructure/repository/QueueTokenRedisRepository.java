package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.queue_token.exception.QueueTokenExpiredException;
import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.infrastructure.persistence.QueueTokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class QueueTokenRedisRepository implements QueueTokenRepository {
    private final SpringQueueTokenRedis redis;

    public QueueTokenRedisRepository(SpringQueueTokenRedis redis) {
        this.redis = redis;
    }

    @Override
    public QueueToken findByTokenId(UUID tokenId) {
        return redis.findById(tokenId)
                .map(this::toDomain)
                .orElseThrow(() -> new QueueTokenExpiredException(ErrorCode.QUEUE_TOKEN_EXPIRED));
    }

    @Override
    public Optional<QueueToken> findByUserId(Long userId) {
        return redis.findByUserId(userId);
    }

    @Override
    public QueueToken save(QueueToken q) {
        QueueTokenEntity e = toEntity(q);
        QueueTokenEntity saved = redis.save(e);

        return toDomain(saved);
    }

    private QueueToken toDomain(QueueTokenEntity e) {
        return QueueToken.reconstitute(
                e.id,
                e.userId,
                e.concertId,
                e.issuedAt,
                e.expiresAt,
                e.tokenType
        );
    }

    private QueueTokenEntity toEntity(QueueToken q) {
        QueueTokenEntity e = new QueueTokenEntity();
        e.id = q.getId();
        e.userId = q.getUserId();
        e.concertId = q.getConcertId();
        e.issuedAt = q.getIssuedAt();
        e.expiresAt = q.getExpiresAt();
        e.tokenType = q.getTokenType();
        return e;
    }
}
