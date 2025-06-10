package kr.hhplus.be.server.config.application.reservation;

import kr.hhplus.be.server.application.reservation.ReservationCommandService;
import kr.hhplus.be.server.domain.repository.ConcertSeatLockRepository;
import kr.hhplus.be.server.domain.repository.ConcertSeatRepository;
import kr.hhplus.be.server.domain.repository.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ReservationConfig {
    private final ReservationRepository reservationRepository;
    private final ConcertSeatLockRepository concertSeatLockRepository;
    private final ConcertSeatRepository concertSeatRepository;

    public ReservationConfig(ReservationRepository reservationRepository, ConcertSeatLockRepository concertSeatLockRepository, ConcertSeatRepository concertSeatRepository) {
        this.reservationRepository = reservationRepository;
        this.concertSeatLockRepository = concertSeatLockRepository;
        this.concertSeatRepository = concertSeatRepository;
    }

    @Bean
    public ReservationCommandService reservationCommandService() {
        return new ReservationCommandService(reservationRepository, concertSeatLockRepository, concertSeatRepository);
    }
}
