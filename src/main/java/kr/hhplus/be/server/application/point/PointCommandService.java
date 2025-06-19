package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.application.point.dto.ChargePointCommand;
import kr.hhplus.be.server.application.point.dto.PointInfo;
import kr.hhplus.be.server.application.point.dto.UsePointCommand;
import kr.hhplus.be.server.common.enums.ReservationStatus;
import kr.hhplus.be.server.domain.concertSeat.model.ConcertSeat;
import kr.hhplus.be.server.domain.concertSeat.repository.ConcertSeatRepository;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;
import kr.hhplus.be.server.domain.pointHistory.repository.PointHistoryRepository;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.reservation.model.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class PointCommandService {
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final ReservationRepository reservationRepository;
    private final ConcertSeatRepository concertSeatRepository;

    public PointCommandService(PointRepository pointRepository, PointHistoryRepository pointHistoryRepository, ReservationRepository reservationRepository, ConcertSeatRepository concertSeatRepository) {
        this.pointRepository = pointRepository;
        this.pointHistoryRepository = pointHistoryRepository;
        this.reservationRepository = reservationRepository;
        this.concertSeatRepository = concertSeatRepository;
    }

    /**
     * 포인트 충전 후 내역을 저장한다.
     * @param command
     * @return
     */
    @Transactional
    public PointInfo charge(Long userId, ChargePointCommand command) {
        Point point = pointRepository.findOrCreatePoint(userId);
        PointHistory history = point.charge(command.amount());

        pointRepository.save(point);
        pointHistoryRepository.save(history);

        return PointInfo.from(history);
    }

    /**
     * 포인트를 사용한다.
     * @param userId
     * @param command
     * @return
     */
    public PointInfo use(Long userId, UsePointCommand command) {
        log.info("userId = {}, reservationId = {}, amount = {}",
                userId, command.reservationId(), command.amount());
        // 예약 정보 확인
        Reservation reservation = reservationRepository.findById(command.reservationId());
        if(isValidReservation(reservation, userId)) {
            reservation.hold();
            reservationRepository.save(reservation);
        }

        Point point = pointRepository.findByUserId(userId);
        if(!point.hasEnoughBalance(command.amount())){
            throw new ApiException(ErrorCode.INSUFFICIENT_POINT);
        }

        PointHistory history = point.use(command.amount());

        pointRepository.save(point);
        pointHistoryRepository.save(history);

        // 예약 확정
        reservation.complete();
        reservationRepository.save(reservation);

        // 좌석 확정
        ConcertSeat concertSeat = concertSeatRepository.findById(reservation.getConcertSeatId());
        concertSeat.reserve();
        concertSeatRepository.save(concertSeat);

        return PointInfo.from(history);
    }

    private boolean isValidReservation(Reservation reservation, Long userId) {
        if(!reservation.getUserId().equals(userId)) {
            throw new ApiException(ErrorCode.RESERVATION_USER_NOT_MATCH);
        }

        if(reservation.getStatus().equals(ReservationStatus.EXPIRED)) {
            throw new ApiException(ErrorCode.RESERVATION_EXPIRED);
        }

        if(!reservation.getStatus().equals(ReservationStatus.AVAILABLE)) {
            log.info("reservation status = {}", reservation.getStatus());
            throw new ApiException(ErrorCode.RESERVATION_INVALID);
        }

        return true;
    }
}
