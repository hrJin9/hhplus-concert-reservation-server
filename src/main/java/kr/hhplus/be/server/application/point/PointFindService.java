package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.application.point.dto.PointBalanceInfo;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;

public class PointFindService {
    private final PointRepository pointRepository;

    public PointFindService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public PointBalanceInfo findByUserId(Long userId) {
        Point point = pointRepository.findByUserId(userId);

        return PointBalanceInfo.from(point);
    }
}
