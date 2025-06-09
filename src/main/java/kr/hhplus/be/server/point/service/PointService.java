package kr.hhplus.be.server.point.service;

import kr.hhplus.be.server.point.service.dto.ChargePointCommand;
import kr.hhplus.be.server.point.service.dto.PointResult;
import kr.hhplus.be.server.point.service.dto.UsePointCommand;

public interface PointService {
    PointResult charge(Long userId, ChargePointCommand command);

    PointResult use(Long userId, UsePointCommand command);
}
