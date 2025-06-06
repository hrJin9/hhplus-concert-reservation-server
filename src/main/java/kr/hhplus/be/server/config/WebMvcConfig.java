package kr.hhplus.be.server.config;

import kr.hhplus.be.server.domain.util.QueueTokenValidator;
import kr.hhplus.be.server.interfaces.web.queue_token.resolver.QueueTokenArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final QueueTokenValidator queueTokenValidator;

    public WebMvcConfig(QueueTokenValidator queueTokenValidator) {
        this.queueTokenValidator = queueTokenValidator;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new QueueTokenArgumentResolver(queueTokenValidator));
    }
}
