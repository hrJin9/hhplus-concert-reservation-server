package kr.hhplus.be.server.interfaces.web.point;

import jakarta.validation.Valid;
import kr.hhplus.be.server.application.point.PointCommandService;
import kr.hhplus.be.server.application.point.dto.PointResult;
import kr.hhplus.be.server.interfaces.web.point.response.PointInfoResponse;
import kr.hhplus.be.server.config.resolver.QueueAuth;
import kr.hhplus.be.server.config.resolver.ValidQueueToken;
import kr.hhplus.be.server.interfaces.web.point.request.ChargePointRequest;
import kr.hhplus.be.server.interfaces.web.point.request.UsePointRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointCommandController {
    private final PointCommandService pointCommandService;

    /**
     * 포인트를 충전한다.
     * @param queueToken
     * @param request
     * @return
     */
    @PostMapping("/charge")
    public ResponseEntity<PointInfoResponse> charge(@QueueAuth ValidQueueToken queueToken,
                                                    @RequestBody @Valid ChargePointRequest request
    ) {
        PointResult result = pointCommandService.charge(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(PointInfoResponse.from(result));
    }
}
