package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.application.point.dto.ChargePointCommand;
import kr.hhplus.be.server.application.point.dto.PointInfo;
import kr.hhplus.be.server.application.point.dto.UsePointCommand;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.pointHistory.model.PointHistory;
import kr.hhplus.be.server.domain.pointHistory.repository.PointHistoryRepository;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import org.springframework.transaction.annotation.Transactional;

public class PointCommandService {
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

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
        Point point = pointRepository.findByUserId(userId);
        PointHistory history = point.use(command.amount());

        pointRepository.save(point);
        pointHistoryRepository.save(history);

        return PointInfo.from(history);
    }
}
