package kr.hhplus.be.server.point.service;

import kr.hhplus.be.server.point.service.dto.ChargePointCommand;
import kr.hhplus.be.server.point.service.dto.PointResult;
import kr.hhplus.be.server.point.service.dto.UsePointCommand;

public interface PointService {
    PointResult charge(ChargePointCommand command);

    PointResult use(UsePointCommand command);
}
