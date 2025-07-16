package kr.hhplus.be.server.config.injection;

import kr.hhplus.be.server.application.reservation.ReservationCommandService;
import kr.hhplus.be.server.application.seat.SeatCommandService;
import kr.hhplus.be.server.domain.seat.repository.SeatLockRepository;
import kr.hhplus.be.server.domain.seat.repository.SeatRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
public class ReservationInjection {
    private final SeatLockRepository seatLockRepository;
    private final SeatCommandService seatCommandService;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    public ReservationInjection(SeatLockRepository seatLockRepository, SeatCommandService seatCommandService, SeatRepository seatRepository, ReservationRepository reservationRepository) {
        this.seatLockRepository = seatLockRepository;
        this.seatCommandService = seatCommandService;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
    }

    @Bean
    public ReservationCommandService reservationCommandService() {
        return new ReservationCommandService(
                seatLockRepository,
                seatCommandService,
                seatRepository,
                reservationRepository
        );
    }
}
