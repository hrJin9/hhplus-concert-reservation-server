package kr.hhplus.be.server.interfaces.web.queue_token;

import kr.hhplus.be.server.application.queue_token.dto.IssueTokenResult;
import kr.hhplus.be.server.interfaces.web.queue_token.request.IssueTokenRequest;
import kr.hhplus.be.server.interfaces.web.queue_token.response.IssueTokenResultResponse;
import kr.hhplus.be.server.application.queue_token.TokenCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/queue-tokens")
@RequiredArgsConstructor
public class TokenCommandController {
    private final TokenCommandService queueTokenCommandService;

    /**
     * 대기열 토큰을 발급한다.
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<IssueTokenResultResponse> issue(@RequestBody IssueTokenRequest request) {
        IssueTokenResult result = queueTokenCommandService.issue(request.toCommand());
        return ResponseEntity.ok(IssueTokenResultResponse.from(result));
    }

}
