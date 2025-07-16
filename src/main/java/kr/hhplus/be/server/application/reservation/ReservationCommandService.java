package kr.hhplus.be.server.application.reservation;

import kr.hhplus.be.server.application.reservation.dto.CancelReservationResult;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationCommand;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationResult;
import kr.hhplus.be.server.application.seat.SeatCommandService;
import kr.hhplus.be.server.application.seat.dto.CancelSeatResult;
import kr.hhplus.be.server.domain.reservation.model.Reservation;
import kr.hhplus.be.server.domain.seat.repository.SeatLockRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class ReservationCommandService {
    private final SeatLockRepository seatLockRepository;
    private final SeatCommandService seatCommandService;
    private final ReservationRepository reservationRepository;

    public ReservationCommandService(SeatLockRepository seatLockRepository, SeatCommandService seatCommandService, ReservationRepository reservationRepository) {
        this.seatLockRepository = seatLockRepository;
        this.seatCommandService = seatCommandService;
        this.reservationRepository = reservationRepository;
    }

    /**
     * 해당 좌석에 대한 Redis락을 획득한다.
     * @param command
     * @return
     */
    public PlaceReservationResult placeWithLock(Long userId, PlaceReservationCommand command) {
        // redis 좌석 락 획득
        boolean lockAcquired = seatLockRepository.acquire(command.seatId(), userId);
        if(!lockAcquired) {
            throw new ApiException(ErrorCode.SEAT_ALREADY_SELECTED);
        }
        try {
            return reserve(userId, command);
        } finally {
            seatLockRepository.release(command.seatId(), userId);
        }
    }

    /**
     * 좌석을 예약 대기 처리한 뒤 예약 내역을 저장한다.
     * @return
     */
    @Transactional
    public PlaceReservationResult reserve(Long userId, PlaceReservationCommand command) {
        // 좌석 예약 대기 처리
        seatCommandService.reserve(userId, command.seatId());

        // 예약 내역 저장
        Reservation reservation = Reservation.create(userId, command.seatId());
        reservation.hold();
        Reservation saved = reservationRepository.save(reservation);

        return PlaceReservationResult.from(saved);
    }

    /**
     * 일정 시간마다 결제 시간이 만료된 결제 대기중인 예약을 취소처리한다.
     */
    @Scheduled(fixedDelay = 5000)
    public void cancelExpiredReservations() {
        List<Reservation> expiredReservations = reservationRepository.findAllExpired();

        for (Reservation reservation : expiredReservations) {
            reservation.expire();
            seatCommandService.expire(reservation.getSeatId());
        }

        reservationRepository.saveAll(expiredReservations);
    }


    /**
     * 결제된 예약을 취소한다.
     * @param userId
     * @param reservationId
     */
    @Transactional
    public CancelReservationResult cancel(Long userId, Long reservationId) {
        // 예약 취소
        Reservation reservation = reservationRepository.findById(reservationId);
        if (!reservation.getUserId().equals(userId)) {
            throw new ApiException(ErrorCode.RESERVATION_USER_NOT_MATCH);
        }
        if (!reservation.isCompleted()) {
            throw new ApiException(ErrorCode.RESERVATION_NOT_COMPLETED);
        }

        reservation.cancel();
        reservationRepository.save(reservation);

        // 좌석 취소
        CancelSeatResult canceledSeat = seatCommandService.cancel(reservation.getSeatId());

        return CancelReservationResult.of(
                reservation.getId(),
                canceledSeat.seatId(),
                reservation.getStatus(),
                canceledSeat.seatStatus()
        );
    }

}
