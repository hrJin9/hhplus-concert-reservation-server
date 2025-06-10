package kr.hhplus.be.server.application.payment;

import kr.hhplus.be.server.application.payment.dto.PlacePaymentCommand;
import kr.hhplus.be.server.application.payment.dto.PlacePaymentResult;
import kr.hhplus.be.server.domain.model.Payment;
import kr.hhplus.be.server.domain.repository.PaymentRepository;

public class PaymentCommandService {
    private final PaymentRepository paymentRepository;

    public PaymentCommandService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PlacePaymentResult place(Long userId, PlacePaymentCommand command) {
        Payment payment = Payment.create(userId, command.amount(), command.paymentMethod());
        Payment saved = paymentRepository.save(payment);

        // TODO: 결제 연동 -> 성공/실패 처리, 성공 후 포인트 충전 이벤트 발행
        saved.success();
        paymentRepository.save(saved);

        return PlacePaymentResult.from(saved);
    }
}
