package kr.hhplus.be.server.point.web;

import jakarta.validation.Valid;
import kr.hhplus.be.server.interfaces.web.queue_token.annotation.QueueAuth;
import kr.hhplus.be.server.interfaces.web.queue_token.resolver.ValidQueueToken;
import kr.hhplus.be.server.point.service.PointService;
import kr.hhplus.be.server.point.service.dto.PointResult;
import kr.hhplus.be.server.point.web.request.ChargePointRequest;
import kr.hhplus.be.server.point.web.request.UsePointRequest;
import kr.hhplus.be.server.point.web.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping("/charge")
    public ResponseEntity<PointResponse> charge(@QueueAuth ValidQueueToken queueToken,
                                                @RequestBody @Valid ChargePointRequest request
    ) {
        PointResult pointResult = pointService.charge(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(PointResponse.from(pointResult));
    }

    @PostMapping("/use")
    public ResponseEntity<PointResponse> use(@QueueAuth ValidQueueToken queueToken,
                                             @RequestBody @Valid UsePointRequest request
    ) {
        PointResult pointResult = pointService.use(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(PointResponse.from(pointResult));
    }
}
