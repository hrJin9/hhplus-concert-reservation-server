package kr.hhplus.be.server.config.application.concert;

import kr.hhplus.be.server.application.concert.ConcertFindService;
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ConcertConfig {
    private final ConcertRepository concertRepository;

    public ConcertConfig(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @Bean
    public ConcertFindService concertFindService() {
        return new ConcertFindService(concertRepository);
    }
}
