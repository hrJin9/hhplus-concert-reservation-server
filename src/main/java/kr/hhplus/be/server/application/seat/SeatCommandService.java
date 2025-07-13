package kr.hhplus.be.server.application.seat;

import kr.hhplus.be.server.common.enums.SeatStatus;
import kr.hhplus.be.server.domain.seat.model.Seat;
import kr.hhplus.be.server.domain.seat.repository.SeatRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SeatCommandService {
    private final SeatRepository seatRepository;

    public SeatCommandService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    /**
     * 좌석을 예약 대기 처리한다.
     * @param userId
     * @param seatId
     */
    public void reserveSeat(Long userId, Long seatId) {
        // 좌석 상태 확인 (AVAILABLE 상태여야 예약 가능)
        Seat seat = seatRepository.findById(seatId);

        if(!seat.isAvailable()){
            throw new ApiException(ErrorCode.SEAT_NOT_AVAILABLE);
        }

        // 좌석 해당 사용자에게 할당 및 좌석 대기 처리
        seat.assignAndHold(userId);
        seatRepository.save(seat);
    }

    /**
     * 좌석을 예약 완료 처리한다.
     * @param userId
     * @param seatId
     */
    public void confirmSeat(Long userId, Long seatId) {
        // 좌석 상태 유효한지 확인
        Seat seat = seatRepository.findById(seatId);
        if (!seat.getAssignedUserId().equals(userId)) {
            throw new ApiException(ErrorCode.SEAT_USER_NOT_MATCH);
        }

        if (!seat.getSeatStatus().equals(SeatStatus.HOLD)) {
            throw new ApiException(ErrorCode.SEAT_NOT_AVAILABLE);
        }

        seat.reserve();
        seatRepository.save(seat);
    }

    /**
     * 일정 시각에 취소된 예약을 일괄적으로 예약 가능하게 변경한다.
     */
    @Scheduled(cron = "0 0 14 * * *", zone = "Asia/Seoul") // TODO : 시간 추후 주입
    @Transactional
    public void releaseExpiredSeats() {
        List<Seat> expiredSeats = seatRepository.findAllBySeatStatus(SeatStatus.EXPIRED);

        for (Seat seat : expiredSeats) {
            seat.release();
        }

        seatRepository.saveAll(expiredSeats);
    }
}
