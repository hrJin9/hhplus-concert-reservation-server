package kr.hhplus.be.server.config.injection;

import kr.hhplus.be.server.application.payment.PaymentCommandService;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PaymentInjection {
    private final PaymentRepository paymentRepository;

    public PaymentInjection(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Bean
    public PaymentCommandService paymentCommandService() {
        return new PaymentCommandService(paymentRepository);
    }
}

