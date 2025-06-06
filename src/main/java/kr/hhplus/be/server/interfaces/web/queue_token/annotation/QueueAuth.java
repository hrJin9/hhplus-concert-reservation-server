package kr.hhplus.be.server.interfaces.web.queue_token.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueueAuth {
}
