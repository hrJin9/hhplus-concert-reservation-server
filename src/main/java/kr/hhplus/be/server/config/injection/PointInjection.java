package kr.hhplus.be.server.config.injection;

import kr.hhplus.be.server.application.point.PointCommandService;
import kr.hhplus.be.server.application.point.PointFindService;
import kr.hhplus.be.server.domain.concertSeat.repository.ConcertSeatRepository;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.pointHistory.repository.PointHistoryRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PointInjection {
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final ReservationRepository reservationRepository;
    private final ConcertSeatRepository concertSeatRepository;

    public PointInjection(PointRepository pointRepository, PointHistoryRepository pointHistoryRepository, ReservationRepository reservationRepository, ConcertSeatRepository concertSeatRepository) {
        this.pointRepository = pointRepository;
        this.pointHistoryRepository = pointHistoryRepository;
        this.reservationRepository = reservationRepository;
        this.concertSeatRepository = concertSeatRepository;
    }

    @Bean
    public PointCommandService pointCommandService() {
        return new PointCommandService(pointRepository, pointHistoryRepository, reservationRepository, concertSeatRepository);
    }

    @Bean
    public PointFindService pointFindService() {
        return new PointFindService(pointRepository);
    }

}
