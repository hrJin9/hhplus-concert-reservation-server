package kr.hhplus.be.server.config.application.payment;

import kr.hhplus.be.server.application.payment.service.PaymentCommandService;
import kr.hhplus.be.server.domain.repository.ConcertSeatRepository;
import kr.hhplus.be.server.domain.repository.PaymentRepository;
import kr.hhplus.be.server.domain.repository.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PaymentConfig {
    private final PaymentRepository paymentRepository;

    public PaymentConfig(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Bean
    public PaymentCommandService paymentCommandService() {
        return new PaymentCommandService(paymentRepository);
    }
}

