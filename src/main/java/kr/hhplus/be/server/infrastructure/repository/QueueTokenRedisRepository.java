package kr.hhplus.be.server.infrastructure.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.common.enums.QueueStatus;
import kr.hhplus.be.server.exception.QueueTokenExpiredException;
import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.infrastructure.persistence.QueueTokenEntity;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class QueueTokenRedisRepository implements QueueTokenRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String QUEUE_TOKEN_KEY = "queue:token:";
    private static final String WAITING_QUEUE_KEY = "queue:waiting";
    private static final String ACTIVE_USERS_KEY = "queue:active";
    private static final String USER_TOKEN_MAPPING_KEY = "queue:user:token:";
    private static final String USER_ACTIVE_KEY = "queue:user:active:";

    public QueueTokenRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public QueueToken findByTokenId(String tokenId) {
        String tokenKey = QUEUE_TOKEN_KEY + tokenId;
        String json = (String) redisTemplate.opsForValue().get(tokenKey);
        if (json == null) {
            throw new QueueTokenExpiredException(ErrorCode.QUEUE_TOKEN_EXPIRED);
        }

        QueueTokenEntity e = toEntity(json);
        return toDomain(e);
    }

    @Override
    public QueueToken findByUserId(Long userId) {
        String tokenIdStr = (String) redisTemplate.opsForValue().get(USER_TOKEN_MAPPING_KEY + userId);
        if (tokenIdStr == null) return null;

        String json = (String) redisTemplate.opsForValue().get(QUEUE_TOKEN_KEY + tokenIdStr);
        if (json == null) return null;

        QueueTokenEntity e = toEntity(json);
        return toDomain(e);
    }

    @Override
    public boolean findQueueStatus() {
        Long activeCount = redisTemplate.opsForSet().size(ACTIVE_USERS_KEY);
        return activeCount != null && activeCount < QueueToken.MAX_ACTIVABLE_USER;
    }

    @Override
    public void addActiveUser(QueueToken token) {
        String tokenKey = QUEUE_TOKEN_KEY + token.getId();

        try {
            String json = objectMapper.writeValueAsString(token);
            Duration ttl = Duration.between(LocalDateTime.now(), token.getExpiresAt());

            redisTemplate.opsForValue().set(tokenKey, json, ttl);
            redisTemplate.opsForValue().set(USER_ACTIVE_KEY + token.getUserId(), token.getId().toString(), ttl);
            redisTemplate.opsForValue().set(USER_TOKEN_MAPPING_KEY + token.getUserId(), token.getId().toString(), ttl);
            redisTemplate.opsForSet().add(ACTIVE_USERS_KEY, token.getUserId().toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize QueueToken", e);
        }
    }


    @Override
    public void activateWaitingUser(){
        String tokenId = (String) redisTemplate.opsForList().leftPop(WAITING_QUEUE_KEY);
        if (tokenId == null) return;

        String tokenKey = QUEUE_TOKEN_KEY + tokenId;
        String json = (String) redisTemplate.opsForValue().get(tokenKey);
        if(json == null) return;

        QueueTokenEntity e = toEntity(json);

        // 상태 업데이트
        e.queueStatus = QueueStatus.ACTIVE;
        e.issuedAt = LocalDateTime.now();
        e.expiresAt = e.issuedAt.plusMinutes(QueueToken.TOKEN_EXPIRE_MIN);

        try {
            String updatedJson = objectMapper.writeValueAsString(e);
            Duration ttl = Duration.between(LocalDateTime.now(), e.expiresAt);

            if (ttl.isNegative() || ttl.isZero()) {
                return;
            }

            redisTemplate.opsForValue().set(tokenKey, updatedJson, ttl);
            redisTemplate.opsForSet().add(ACTIVE_USERS_KEY, e.userId.toString());
            redisTemplate.opsForValue().set(USER_ACTIVE_KEY + e.userId, e.id.toString(), ttl);
            redisTemplate.opsForValue().set(USER_TOKEN_MAPPING_KEY + e.userId, e.id.toString(), ttl);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Failed to serialize QueueToken", exception);
        }
    }

    @Override
    public void addWaitingUserToQueue(QueueToken token) {
        String tokenKey = QUEUE_TOKEN_KEY + token.getId();

        try {
            String json = objectMapper.writeValueAsString(token);
            Duration ttl = Duration.between(LocalDateTime.now(), token.getExpiresAt());

            redisTemplate.opsForList().rightPush(WAITING_QUEUE_KEY, token.getId().toString());
            redisTemplate.opsForValue().set(tokenKey, json, ttl);
            redisTemplate.opsForValue().set(USER_TOKEN_MAPPING_KEY + token.getUserId(), token.getId().toString(), ttl);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize QueueToken", e);
        }
    }

    @Override
    public int getQueuePosition(String tokenId) {
        List<Object> waitingList = redisTemplate.opsForList().range(WAITING_QUEUE_KEY, 0, -1);
        if (waitingList == null) return -1;

        for (int i = 0; i < waitingList.size(); i++) {
            if (tokenId.equals(waitingList.get(i))) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public void cleanQueue() {
        List<Object> allWaitingTokens = redisTemplate.opsForList().range(WAITING_QUEUE_KEY, 0, -1);

        if (allWaitingTokens == null)
            return;

        for (Object tokenObj : allWaitingTokens) {
            String tokenId = (String) tokenObj;
            String tokenKey = QUEUE_TOKEN_KEY + tokenId;

            Boolean exists = redisTemplate.hasKey(tokenKey);

            if (Boolean.FALSE.equals(exists)) {
                // 만료되었으면 list에서 제거
                redisTemplate.opsForList().remove(WAITING_QUEUE_KEY, 0, tokenId);
            }
        }
    }

    @Override
    public void cleanExpiredToken() {
        Set<Object> activeUsers = redisTemplate.opsForSet().members(ACTIVE_USERS_KEY);
        if (activeUsers == null) return;

        for (Object userObj : activeUsers) {
            String userId = (String) userObj;
            String tokenId = (String) redisTemplate.opsForValue().get(USER_ACTIVE_KEY + userId);

            if (tokenId == null || !redisTemplate.hasKey(QUEUE_TOKEN_KEY + tokenId)) {
                redisTemplate.opsForSet().remove(ACTIVE_USERS_KEY, userId);
            }
        }
    }

    private QueueToken toDomain(QueueTokenEntity e) {
        return QueueToken.of(
                e.id,
                e.userId,
                e.queueStatus,
                e.issuedAt,
                e.expiresAt
        );
    }

    private QueueTokenEntity toEntity(String json) {
        try {
            return objectMapper.readValue(json, QueueTokenEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("토큰 역직렬화에 실패하였습니다.", e);
        }
    }

}
