package kr.hhplus.be.server.point.service.impl;

import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.domain.exception.InsufficientPointException;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.domain.PointHistory;
import kr.hhplus.be.server.point.repository.PointHistoryRepository;
import kr.hhplus.be.server.point.repository.PointRepository;
import kr.hhplus.be.server.point.service.PointService;
import kr.hhplus.be.server.point.service.dto.ChargePointCommand;
import kr.hhplus.be.server.point.service.dto.PointResult;
import kr.hhplus.be.server.point.service.dto.UsePointCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    /**
     * 포인트 충전 후 내역을 저장한다.
     * @param command
     * @return
     */
    @Override
    @Transactional
    public PointResult charge(Long userId, ChargePointCommand command) {
        Point point = getOrCreatePoint(userId);

        PointHistory history = point.charge(command.amount());

        pointRepository.save(point);
        pointHistoryRepository.save(history);

        return PointResult.of(history);
    }

    /**
     * 포인트를 사용한다.
     * @param command
     * @return
     */
    @Override
    public PointResult use(Long userId, UsePointCommand command) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorCode.POINT_NOT_FOUND));

        try {
            PointHistory history = point.use(command.amount());

            pointRepository.save(point);
            pointHistoryRepository.save(history);

            return PointResult.of(history);
        } catch (InsufficientPointException e) {
            // TODO : 포인트 부족 -> 결제하기
            throw new ApiException(HttpStatus.BAD_REQUEST, e.getErrorCode());
        }
    }

    /**
     * 저장되어있는 포인트를 반환하거나 생성한다.
     * @param userId
     * @return
     */
    private Point getOrCreatePoint(Long userId) {
        return pointRepository.findByUserId(userId)
                .orElse(Point.create(userId));
    }
}
