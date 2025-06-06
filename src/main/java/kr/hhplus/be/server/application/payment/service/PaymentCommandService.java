package kr.hhplus.be.server.application.payment.service;

import kr.hhplus.be.server.application.payment.dto.PlacePaymentCommand;
import kr.hhplus.be.server.application.payment.dto.PaymentResult;
import kr.hhplus.be.server.common.enums.PaymentStatus;
import kr.hhplus.be.server.domain.model.Payment;
import kr.hhplus.be.server.domain.repository.PaymentRepository;

public class PaymentCommandService {
    private final PaymentRepository paymentRepository;

    public PaymentCommandService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentResult placePayment(Long userId, PlacePaymentCommand command) {
        Payment payment = Payment.create(userId, command.amount(), command.paymentMethod());
        Payment saved = paymentRepository.save(payment);

        // TODO: 결제 연동
        return PaymentResult.success(saved.getId(), PaymentStatus.SUCCESS);
        // TODO : 결제 성공 -> 포인트 충전 이벤트 발행?
    }
}
