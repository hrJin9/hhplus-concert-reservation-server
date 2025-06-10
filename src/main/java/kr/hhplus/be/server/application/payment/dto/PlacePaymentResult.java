package kr.hhplus.be.server.application.payment.dto;

import kr.hhplus.be.server.common.enums.PaymentStatus;
import kr.hhplus.be.server.domain.model.Payment;

public record PlacePaymentResult(
        Long paymentId,
        PaymentStatus paymentStatus
) {
    public static PlacePaymentResult from(Payment payment) {
        return new PlacePaymentResult(
                payment.getId(),
                payment.getPaymentStatus()
        );
    }
}
