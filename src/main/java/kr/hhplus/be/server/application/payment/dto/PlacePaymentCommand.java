package kr.hhplus.be.server.application.payment.dto;

import kr.hhplus.be.server.common.enums.PaymentMethod;

public record PlacePaymentCommand(
        Long amount,
        PaymentMethod paymentMethod
) {
    public static PlacePaymentCommand of(Long amount, PaymentMethod paymentMethod) {
        return new PlacePaymentCommand(
                amount,
                paymentMethod
        );
    }

}
