package kr.hhplus.be.server.interfaces.web.queue_token;

import kr.hhplus.be.server.application.queue_token.dto.QueueStatus;
import kr.hhplus.be.server.application.queue_token.TokenFindService;
import kr.hhplus.be.server.interfaces.web.queue_token.annotation.QueueAuth;
import kr.hhplus.be.server.interfaces.web.queue_token.resolver.ValidQueueToken;
import kr.hhplus.be.server.interfaces.web.queue_token.response.QueueStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/queue-tokens")
@RequiredArgsConstructor
public class QueueFindController {
    private final TokenFindService queueTokenFindService;

    /**
     * 대기열 상태를 조회한다.
     * @param request
     * @return
     */
    @GetMapping("/status")
    public ResponseEntity<QueueStatusResponse> findQueueStatus(@QueueAuth ValidQueueToken token) {
        QueueStatus status = queueTokenFindService.findQueueStatus(token.userId());

        return ResponseEntity.ok(QueueStatusResponse.from(status));
    }
}
