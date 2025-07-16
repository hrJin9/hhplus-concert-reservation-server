package kr.hhplus.be.server.domain.queue_token.repository;

import kr.hhplus.be.server.domain.queue_token.model.QueueToken;

import java.util.Optional;
import java.util.UUID;

public interface QueueTokenRepository {
    QueueToken findByTokenId(String tokenId);

    QueueToken findByUserId(Long userId);

    void addActiveUser(QueueToken token);

    void activateWaitingUser();

    void addWaitingUserToQueue(QueueToken token);

    boolean findQueueStatus();

    int getQueuePosition(String tokenId);

    void cleanQueue();

    void cleanExpiredToken();

}
