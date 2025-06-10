package kr.hhplus.be.server.interfaces.web.point;

import jakarta.validation.Valid;
import kr.hhplus.be.server.application.point.PointCommandService;
import kr.hhplus.be.server.application.point.dto.PointInfo;
import kr.hhplus.be.server.interfaces.web.point.response.PointInfoResponse;
import kr.hhplus.be.server.interfaces.web.queue_token.annotation.QueueAuth;
import kr.hhplus.be.server.interfaces.web.queue_token.resolver.ValidQueueToken;
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
        PointInfo result = pointCommandService.charge(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(PointInfoResponse.from(result));
    }

    /**
     * 포인트를 사용한다.
     * @param queueToken
     * @param request
     * @return
     */
    @PostMapping("/use")
    public ResponseEntity<PointInfoResponse> use(@QueueAuth ValidQueueToken queueToken,
                                                 @RequestBody @Valid UsePointRequest request
    ) {
        PointInfo result = pointCommandService.use(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(PointInfoResponse.from(result));
    }
}
