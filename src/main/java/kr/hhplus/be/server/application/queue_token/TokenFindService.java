package kr.hhplus.be.server.application.queue_token;

import kr.hhplus.be.server.application.queue_token.dto.QueueStatus;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;

public class TokenFindService {
    private final QueueTokenRepository queueTokenRepository;

    public TokenFindService(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }


    public QueueStatus findQueueStatus(Long userId) {
        // TODO: 대기열 조회 기능 추가
        return  null;
    }
}
