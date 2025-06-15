package kr.hhplus.be.server.interfaces.web.queue_token.resolver;

import jakarta.servlet.http.HttpServletRequest;
import kr.hhplus.be.server.domain.queue_token.exception.QueueTokenExpiredException;
import kr.hhplus.be.server.domain.queue_token.model.QueueToken;
import kr.hhplus.be.server.domain.queue_token.util.QueueTokenValidator;
import kr.hhplus.be.server.exception.ApiException;
import kr.hhplus.be.server.exception.ErrorCode;
import kr.hhplus.be.server.interfaces.web.queue_token.annotation.QueueAuth;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

public class QueueTokenArgumentResolver implements HandlerMethodArgumentResolver {
    private final QueueTokenValidator queueTokenValidator;

    public QueueTokenArgumentResolver(QueueTokenValidator queueTokenValidator) {
        this.queueTokenValidator = queueTokenValidator;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ValidQueueToken.class)
                && parameter.hasParameterAnnotation(QueueAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String tokenHeader = request.getHeader("Queue-Token");

        if (tokenHeader == null) {
            throw new ApiException(ErrorCode.QUEUE_TOKEN_MISSING);
        }

        UUID tokenId = UUID.fromString(tokenHeader);
        QueueToken token = queueTokenValidator.validate(tokenId);

        return new ValidQueueToken(token.getUserId(), token.getIssuedAt(), token.getExpiresAt());
    }

}
