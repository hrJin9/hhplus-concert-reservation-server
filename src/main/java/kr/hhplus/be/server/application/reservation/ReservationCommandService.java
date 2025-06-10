package kr.hhplus.be.server.application.reservation;

import kr.hhplus.be.server.application.reservation.dto.PlaceReservationCommand;
import kr.hhplus.be.server.application.reservation.dto.PlaceReservationResult;
import kr.hhplus.be.server.domain.model.ConcertSeat;
import kr.hhplus.be.server.domain.model.Reservation;
import kr.hhplus.be.server.domain.repository.ConcertSeatLockRepository;
import kr.hhplus.be.server.domain.repository.ConcertSeatRepository;
import kr.hhplus.be.server.domain.repository.ReservationRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
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
    public PlaceReservationResult place(Long userId, PlaceReservationCommand command) {
        // 좌석 상태 확인
        ConcertSeat concertSeat = concertSeatRepository.findById(userId);

        if(!concertSeat.isAvailable()){
            throw new ApiException(ErrorCode.SEAT_NOT_AVAILABLE);
        }

        // redis 좌석 락 획득
        boolean lockAcquired = seatLockRepository.acquire(command.concertSeatId(), userId);
        if(!lockAcquired) {
            throw new ApiException(ErrorCode.SEAT_ALREADY_SELECTED);
        }

        // 좌석 상태 업데이트
        concertSeat.hold();
        concertSeatRepository.update(concertSeat);

        // 예약
        Reservation reservation = Reservation.create(userId, command.concertSeatId());
        Reservation saved = reservationRepository.save(reservation);

        // 좌석 상태 업데이트
        concertSeat.reserved();
        concertSeatRepository.update(concertSeat);

        // TODO : 포인트 사용 일정 시간 이내에 안했을 때 좌석 상태 EXPIRED로 변경.
        // 좌석 락 해제
        seatLockRepository.release(command.concertSeatId(), userId);

        // TODO : 포인트 사용 이벤트 발행
        return PlaceReservationResult.from(saved);
    }
}
