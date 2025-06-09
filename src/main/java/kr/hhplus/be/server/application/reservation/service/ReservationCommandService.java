package kr.hhplus.be.server.application.reservation.service;

import kr.hhplus.be.server.application.reservation.dto.CreateReservationCommand;
import kr.hhplus.be.server.application.reservation.dto.ReservationResult;
import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.domain.model.ConcertSeat;
import kr.hhplus.be.server.domain.model.Reservation;
import kr.hhplus.be.server.domain.repository.ConcertSeatLockRepository;
import kr.hhplus.be.server.domain.repository.ConcertSeatRepository;
import kr.hhplus.be.server.domain.repository.ReservationRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

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
     * 좌석 예약 유스케이스
     * @param command
     * @return
     */
    @Transactional
    public ReservationResult placeReservation(Long userId, CreateReservationCommand command) {
        // 좌석 상태 확인
        ConcertSeat concertSeat = concertSeatRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorCode.SEAT_NOT_FOUND));

        if(!concertSeat.isAvailable()){
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, ErrorCode.SEAT_NOT_AVAILABLE);
        }

        // redis 좌석 락 획득
        boolean lockAcquired = seatLockRepository.acquire(command.concertSeatId(), userId);
        if(!lockAcquired) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, ErrorCode.SEAT_NOT_ACCEPTABLE);
        }

        try {
            // 좌석 상태 업데이트
            concertSeat.hold();
            concertSeatRepository.update(concertSeat);

            // 예약
            Reservation reservation = Reservation.create(userId, command.concertSeatId());
            Reservation savedReservation = reservationRepository.save(reservation);

            // 좌석 상태 업데이트
            concertSeat.reserved();
            concertSeatRepository.update(concertSeat);

            return ReservationResult.success(savedReservation.getId(), ReservationStatus.SUCCESS);
            // TODO : 포인트 사용 이벤트 발행

            // TODO : 포인트 사용 일정 시간 이내에 안했을 때 좌석 상태 EXPIRED로 변경.
        } catch (Exception e) {
            // TODO: 도메인 예외를 어떻게 응답해야하는지
            return ReservationResult.failed(ReservationStatus.FAILED);
        } finally {
            // 좌석 락 해제
            seatLockRepository.release(command.concertSeatId(), userId);
        }
    }
}
