package kr.hhplus.be.server.application.queue_token;

import kr.hhplus.be.server.application.queue_token.dto.QueueStatusResult;
import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;

public class TokenFindService {
    private final QueueTokenRepository queueTokenRepository;

    public TokenFindService(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }


    public QueueStatusResult findQueueStatus(Long userId) {
        QueueToken queueToken = queueTokenRepository.findByUserId(userId);

        int position = queueTokenRepository.getQueuePosition(queueToken.getId());
        return QueueStatusResult.of(userId, position);

    }
}
