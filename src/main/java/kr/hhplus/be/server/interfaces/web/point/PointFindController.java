package kr.hhplus.be.server.interfaces.web.point;

import kr.hhplus.be.server.application.point.PointFindService;
import kr.hhplus.be.server.application.point.dto.PointBalanceInfo;
import kr.hhplus.be.server.interfaces.web.point.response.PointBalanceResponse;
import kr.hhplus.be.server.interfaces.web.queue_token.annotation.QueueAuth;
import kr.hhplus.be.server.interfaces.web.queue_token.resolver.ValidQueueToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/points")
public class PointFindController {
    private final PointFindService pointFindService;

    public PointFindController(PointFindService pointFindService) {
        this.pointFindService = pointFindService;
    }

    /**
     * 유저의 포인트를 조회한다.
     * @param queueToken
     * @return
     */
    @GetMapping
    public ResponseEntity<PointBalanceResponse> findPointByUserId(@QueueAuth ValidQueueToken queueToken) {
        PointBalanceInfo pointInfo = pointFindService.findByUserId(queueToken.userId());

        return ResponseEntity.ok(PointBalanceResponse.from(pointInfo));

    }

}
