package kr.hhplus.be.server.interfaces.web.payment.response;

import kr.hhplus.be.server.application.payment.dto.PaymentResult;
import kr.hhplus.be.server.common.enums.PaymentStatus;

public record PaymentResponse(
        Long paymentId,
        PaymentStatus paymentStatus
) {
    public static PaymentResponse from(PaymentResult paymentResult) {
        return new PaymentResponse(
                paymentResult.paymentId(),
                paymentResult.paymentStatus()
        );
    }
}
