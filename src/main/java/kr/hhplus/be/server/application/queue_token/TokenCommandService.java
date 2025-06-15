package kr.hhplus.be.server.application.queue_token;

import kr.hhplus.be.server.application.queue_token.dto.IssueTokenCommand;
import kr.hhplus.be.server.application.queue_token.dto.IssueTokenResult;
import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.domain.queue_token.repository.QueueTokenRepository;

import java.util.Optional;

public class TokenCommandService {
    private final QueueTokenRepository queueTokenRepository;

    public TokenCommandService(QueueTokenRepository queueTokenRepository) {
        this.queueTokenRepository = queueTokenRepository;
    }

    /**
     * 대기열 토큰을 발급한다.
     * @param command
     * @return
     */
    public IssueTokenResult issue(IssueTokenCommand command) {
        Optional<QueueToken> existToken = queueTokenRepository.findByUserId(command.userId());
        if(existToken.isPresent()) {
            return IssueTokenResult.from(existToken.get());
        }

        QueueToken token = QueueToken.create(command.userId(), command.concertId());
        QueueToken savedToken = queueTokenRepository.save(token);

        // TODO : 발급된 토큰 정보 + 대기열 상태를 함께 응답해야하는게 아닌지..
        return IssueTokenResult.from(savedToken);
    }
}
