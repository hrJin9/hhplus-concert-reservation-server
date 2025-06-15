package kr.hhplus.be.server.config.application.point;

import kr.hhplus.be.server.application.point.PointCommandService;
import kr.hhplus.be.server.application.point.PointFindService;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.pointHistory.repository.PointHistoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PointConfig {
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public PointConfig(PointRepository pointRepository, PointHistoryRepository pointHistoryRepository) {
        this.pointRepository = pointRepository;
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Bean
    public PointCommandService pointCommandService() {
        return new PointCommandService(pointRepository, pointHistoryRepository);
    }

    @Bean
    public PointFindService pointFindService() {
        return new PointFindService(pointRepository);
    }

}
