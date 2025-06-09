package kr.hhplus.be.server.application.payment.dto;

import kr.hhplus.be.server.common.enums.PaymentStatus;

public record PaymentResult(
        Long paymentId,
        PaymentStatus paymentStatus
) {
    public static PaymentResult success(Long paymentId, PaymentStatus paymentStatus) {
        return new PaymentResult(
                paymentId,
                paymentStatus
        );
    }
}
