package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.application.point.dto.ChargePointCommand;
import kr.hhplus.be.server.application.point.dto.PointResult;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;
import kr.hhplus.be.server.domain.pointHistory.repository.PointHistoryRepository;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class PointCommandService {
    private final PointHistoryRepository pointHistoryRepository;
    private final PointRepository pointRepository;

    public PointCommandService(PointRepository pointRepository, PointHistoryRepository pointHistoryRepository) {
        this.pointRepository = pointRepository;
        this.pointHistoryRepository = pointHistoryRepository;
    }

    /**
     * 포인트 충전 후 내역을 저장한다.
     * @param command
     * @return
     */
    @Transactional
    public PointResult charge(Long userId, ChargePointCommand command) {
        Point point = pointRepository.findOrCreatePoint(userId);
        PointHistory history = point.charge(command.amount());

        pointRepository.save(point);
        pointHistoryRepository.save(history);

        return PointResult.from(history);
    }

    /**
     * 포인트를 사용한다.
     * @param userId
     * @param command
     * @return
     */
    @Transactional
    public PointResult use(Long userId, Long amount) {
        Point point = pointRepository.findByUserId(userId);
        if(!point.hasEnoughBalance(amount)){
            throw new ApiException(ErrorCode.INSUFFICIENT_POINT);
        }

        PointHistory history = point.use(amount);

        pointRepository.save(point);
        pointHistoryRepository.save(history);

        return PointResult.from(history);
    }
}
