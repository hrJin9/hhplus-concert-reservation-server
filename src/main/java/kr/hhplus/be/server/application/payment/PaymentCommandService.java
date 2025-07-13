package kr.hhplus.be.server.application.payment;

import kr.hhplus.be.server.application.payment.dto.PlacePaymentCommand;
import kr.hhplus.be.server.application.payment.dto.PlacePaymentResult;
import kr.hhplus.be.server.application.point.PointCommandService;
import kr.hhplus.be.server.application.seat.SeatCommandService;
import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.domain.reservation.model.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.domain.payment.model.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import kr.hhplus.be.server.domain.point.repository.PointLockRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationLockRepository;
import kr.hhplus.be.server.domain.seat.repository.SeatLockRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class PaymentCommandService {
    private final SeatLockRepository seatLockRepository;
    private final ReservationLockRepository reservationLockRepository;
    private final PointLockRepository pointLockRepository;
    private final ReservationRepository reservationRepository;
    private final PointCommandService pointCommandService;
    private final SeatCommandService seatCommandService;
    private final PaymentRepository paymentRepository;

    public PaymentCommandService(SeatLockRepository seatLockRepository, ReservationLockRepository reservationLockRepository, PointLockRepository pointLockRepository, ReservationRepository reservationRepository, PointCommandService pointCommandService, SeatCommandService seatCommandService, PaymentRepository paymentRepository) {
        this.seatLockRepository = seatLockRepository;
        this.reservationLockRepository = reservationLockRepository;
        this.pointLockRepository = pointLockRepository;
        this.reservationRepository = reservationRepository;
        this.pointCommandService = pointCommandService;
        this.seatCommandService = seatCommandService;
        this.paymentRepository = paymentRepository;
    }

    /**
     * 예약 중복 결제를 방지하기 위해 좌석, 예약, 포인트에 대한 락을 획득한다.
     * @param userId
     * @param reservationId
     */
    public PlacePaymentResult placeWithLock(Long userId, PlacePaymentCommand command) {
        boolean reservationLockAcquired = reservationLockRepository.acquire(command.reservationId(), userId);
        boolean pointLockAquired = pointLockRepository.acquire(userId);

        Long seatId = reservationRepository.findById(command.reservationId()).getSeatId();
        boolean seatLockAcquired = seatLockRepository.acquire(seatId, userId);

        if(!reservationLockAcquired || !pointLockAquired || !seatLockAcquired) {
            throw new ApiException(ErrorCode.RESERVATION_IN_PROCESS);
        }

        try {
            return this.pay(userId, command);
        } finally {
            reservationLockRepository.release(command.reservationId(), userId);
            pointLockRepository.release(userId);
            seatLockRepository.release(seatId, userId);
        }
    }

    /**
     * 예약을 결제한다.
     * @param userId
     * @param command
     * @return
     */
    @Transactional
    public PlacePaymentResult pay(Long userId, PlacePaymentCommand command) {
        // 예약 상태 확인
        Reservation reservation = reservationRepository.findById(command.reservationId());
        if (!reservation.getStatus().equals(ReservationStatus.HOLD)) {
            throw new ApiException(ErrorCode.RESERVATION_INVALID);
        }

        // 포인트 잔액 확인 및 사용
        pointCommandService.use(userId, command.amount());

        // 결제 내역 저장
        Payment payment = Payment.create(userId, command.amount(), command.paymentMethod()); // TODO : 추후 결제방법에 따라서 결제 상태를 처리..
        Payment savedPayment = paymentRepository.save(payment);

        // 예약 확정
        reservation.complete();
        reservationRepository.save(reservation);

        // 좌석 확정
        seatCommandService.confirmSeat(userId, reservation.getSeatId());

        return PlacePaymentResult.from(savedPayment);
    }
}
