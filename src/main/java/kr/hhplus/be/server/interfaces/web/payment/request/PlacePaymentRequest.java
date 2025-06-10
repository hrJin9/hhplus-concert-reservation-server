package kr.hhplus.be.server.interfaces.web.payment.request;

import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.application.payment.dto.PlacePaymentCommand;
import kr.hhplus.be.server.common.enums.PaymentMethod;

public record PlacePaymentRequest(
        @NotNull(message = "결제금액은 필수 값입니다.")
        Long amount,
        @NotNull(message = "결제수단은 필수 값입니다.")
        PaymentMethod paymentMethod
) {
        public PlacePaymentCommand toCommand() {
                return new PlacePaymentCommand(
                        amount,
                        paymentMethod
                );
        }
}
