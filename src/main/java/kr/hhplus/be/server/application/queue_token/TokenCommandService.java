package kr.hhplus.be.server.application.queue_token;

import kr.hhplus.be.server.application.queue_token.dto.IssueTokenCommand;
import kr.hhplus.be.server.application.queue_token.dto.IssueTokenResult;
import kr.hhplus.be.server.application.queue_token.dto.QueueStatusResult;
import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.domain.queue_token.repository.QueueLockRepository;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import org.springframework.scheduling.annotation.Scheduled;

public class TokenCommandService {
    private final QueueTokenRepository queueTokenRepository;
    private final QueueLockRepository queueLockRepository;

    public TokenCommandService(QueueTokenRepository queueTokenRepository, QueueLockRepository queueLockRepository) {
        this.queueTokenRepository = queueTokenRepository;
        this.queueLockRepository = queueLockRepository;
    }

    /**
     * 대기열 토큰을 발급한다.
     *
     * @param command
     * @return s
     */
    public IssueTokenResult issue(IssueTokenCommand command) {
        QueueToken existToken = queueTokenRepository.findByUserId(command.userId());
        if (existToken != null) {
            return IssueTokenResult.from(existToken);
        }

        // 대기열 조회하기위해 락 (분산환경)
        boolean isLocked = queueLockRepository.tryLock();
        if (!isLocked) {
            throw new ApiException(ErrorCode.QUEUE_LOCK_FAILED);
        }

        // 활성화 여부 판단
        QueueToken token;
        boolean isActivable = queueTokenRepository.findQueueStatus();
        if (isActivable) {
            // 즉시 활성화
            token = QueueToken.createActiveToken(command.userId());
            queueTokenRepository.addActiveUser(token);
        } else {
            // 대기열에 추가
            token = QueueToken.createWaitingToken(command.userId());
            queueTokenRepository.addWaitingUserToQueue(token);
        }

        // 대기열 락 해제
        queueLockRepository.release();

        return IssueTokenResult.from(token);
    }


    /**
     * 큐 상태 점검 + 대기열 사용자 업데이트
     */
    @Scheduled(fixedDelay = 5000)
    public void activateWaitingUsers() {
        boolean isLocked = queueLockRepository.tryLock();
        if (!isLocked) return;

        try {
            queueTokenRepository.cleanQueue();
            queueTokenRepository.cleanExpiredToken();

            while (queueTokenRepository.findQueueStatus()) {
                queueTokenRepository.activateWaitingUser();
            }
        } finally {
            queueLockRepository.release();
        }
    }
}
