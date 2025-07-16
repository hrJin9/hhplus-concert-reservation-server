package kr.hhplus.be.server.domain.queue_token.service;

import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;

public class QueueTokenDomainService {
    private final QueueTokenRepository queueTokenRepository;
    private static final Integer AVAILABLE_ACTIVE_TOKEN = 50;

    public QueueTokenDomainService(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }


}
