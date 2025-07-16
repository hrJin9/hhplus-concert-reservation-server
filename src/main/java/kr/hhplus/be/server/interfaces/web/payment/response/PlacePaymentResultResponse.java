package kr.hhplus.be.server.interfaces.web.payment.response;

import kr.hhplus.be.server.application.payment.dto.PlacePaymentResult;
import kr.hhplus.be.server.common.enums.PaymentStatus;

public record PlacePaymentResultResponse(
        Long paymentId,
        PaymentStatus paymentStatus
) {
    public static PlacePaymentResultResponse from(PlacePaymentResult result) {
        return new PlacePaymentResultResponse(
                result.paymentId(),
                result.paymentStatus()
        );
    }
}
