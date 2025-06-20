package kr.hhplus.be.server.application.reservation;

import kr.hhplus.be.server.application.reservation.dto.PlaceReservationCommand;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationResult;
import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.domain.concertSeat.model.ConcertSeat;
import kr.hhplus.be.server.domain.reservation.model.Reservation;
import kr.hhplus.be.server.domain.concertSeat.repository.ConcertSeatLockRepository;
import kr.hhplus.be.server.domain.concertSeat.repository.ConcertSeatRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationCommandService {
    private final ReservationRepository reservationRepository;
    private final ConcertSeatLockRepository seatLockRepository;
    private final ConcertSeatRepository concertSeatRepository;

    public ReservationCommandService(ReservationRepository reservationRepository, ConcertSeatLockRepository seatLockRepository, ConcertSeatRepository concertSeatRepository) {
        this.reservationRepository = reservationRepository;
        this.seatLockRepository = seatLockRepository;
        this.concertSeatRepository = concertSeatRepository;
    }

    /**
     * 좌석 예약
     * @param command
     * @return
     */
    @Transactional
    public PlaceReservationResult reserve(Long userId, PlaceReservationCommand command) {
        // redis 좌석 락 획득
        boolean lockAcquired = seatLockRepository.acquire(command.concertSeatId(), userId);
        try {
            if(!lockAcquired) {
                throw new ApiException(ErrorCode.SEAT_ALREADY_SELECTED);
            }

            // 좌석 상태 확인
            ConcertSeat concertSeat = concertSeatRepository.findById(command.concertSeatId());
            if(!concertSeat.isAvailable()){
                if(concertSeat.isExpired()) { // TODO : 만료처리 (스케줄러 등)
                    concertSeat.release();
                    concertSeatRepository.save(concertSeat);
                } else {
                    throw new ApiException(ErrorCode.SEAT_NOT_AVAILABLE);
                }
            }

            // 좌석 대기 상태 업데이트
            concertSeat.hold();
            concertSeatRepository.save(concertSeat);

            // 예약
            Reservation reservation = Reservation.create(userId, command.concertSeatId());
            Reservation saved = reservationRepository.save(reservation);

            // TODO : 포인트 사용 일정 시간 이내에 안했을 때 좌석 상태 EXPIRED로 변경.
            return PlaceReservationResult.from(saved);

        } finally {
            // 좌석 락 해제
            if(lockAcquired) {
                seatLockRepository.release(command.concertSeatId(), userId);
            }
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void cancelExpiredReservations() {
        LocalDateTime timeoutThreshold = LocalDateTime.now().minusMinutes(5);
        List<Reservation> expiredReservations = reservationRepository
                .findAllByStatusAndReservedAtBefore(ReservationStatus.HOLD, timeoutThreshold);


        for (Reservation reservation : expiredReservations) {
            reservation.cancel();
        }

        // 3. 일괄 저장
        reservationRepository.saveAll(expiredReservations);
    }
}
