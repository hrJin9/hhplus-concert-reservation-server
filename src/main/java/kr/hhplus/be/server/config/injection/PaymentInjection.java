package kr.hhplus.be.server.config.injection;

import kr.hhplus.be.server.application.payment.PaymentCommandService;
import kr.hhplus.be.server.application.point.PointCommandService;
import kr.hhplus.be.server.application.seat.SeatCommandService;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import kr.hhplus.be.server.domain.point.repository.PointLockRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationLockRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.domain.seat.repository.SeatLockRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PaymentInjection {
    private final SeatLockRepository seatLockRepository;
    private final ReservationLockRepository reservationLockRepository;
    private final PointLockRepository pointLockRepository;
    private final ReservationRepository reservationRepository;
    private final PointCommandService pointCommandService;
    private final SeatCommandService seatCommandService;
    private final PaymentRepository paymentRepository;

    public PaymentInjection(SeatLockRepository seatLockRepository, ReservationLockRepository reservationLockRepository, PointLockRepository pointLockRepository, ReservationRepository reservationRepository, PointCommandService pointCommandService, SeatCommandService seatCommandService, PaymentRepository paymentRepository) {
        this.seatLockRepository = seatLockRepository;
        this.reservationLockRepository = reservationLockRepository;
        this.pointLockRepository = pointLockRepository;
        this.reservationRepository = reservationRepository;
        this.pointCommandService = pointCommandService;
        this.seatCommandService = seatCommandService;
        this.paymentRepository = paymentRepository;
    }

    @Bean
    public PaymentCommandService paymentCommandService() {
        return new PaymentCommandService(
                seatLockRepository,
                reservationLockRepository,
                pointLockRepository,
                reservationRepository,
                pointCommandService,
                seatCommandService,
                paymentRepository
        );
    }
}

