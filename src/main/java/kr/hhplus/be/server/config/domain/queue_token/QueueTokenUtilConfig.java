package kr.hhplus.be.server.config.domain.queue_token;

import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;
import kr.hhplus.be.server.domain.queue_token.util.QueueTokenValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueTokenUtilConfig {
    private final QueueTokenRepository queueTokenRepository;

    public QueueTokenUtilConfig(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }

    @Bean
    public QueueTokenValidator queueTokenValidator() {
        return new QueueTokenValidator(queueTokenRepository);
    }
}
