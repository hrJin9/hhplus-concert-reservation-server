package kr.hhplus.be.server.config.application.queue_token;

import kr.hhplus.be.server.application.queue_token.TokenCommandService;
import kr.hhplus.be.server.application.queue_token.TokenFindService;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class QueueTokenConfig {
    private final QueueTokenRepository queueTokenRepository;

    public QueueTokenConfig(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }

    @Bean
    public TokenCommandService queueTokenCommandService() {
        return new TokenCommandService(queueTokenRepository);
    }

    @Bean
    public TokenFindService queueTokenFindService() {
        return new TokenFindService(queueTokenRepository);
    }
}
