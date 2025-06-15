package kr.hhplus.be.server.interfaces.web.payment;

import jakarta.validation.Valid;
import kr.hhplus.be.server.application.payment.dto.PlacePaymentResult;
import kr.hhplus.be.server.application.payment.PaymentCommandService;
import kr.hhplus.be.server.interfaces.web.payment.request.PlacePaymentRequest;
import kr.hhplus.be.server.interfaces.web.payment.response.PlacePaymentResultResponse;
import kr.hhplus.be.server.interfaces.web.queue_token.annotation.QueueAuth;
import kr.hhplus.be.server.interfaces.web.queue_token.resolver.ValidQueueToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentCommandController {
    private final PaymentCommandService paymentUseCase;

    @PostMapping
    public ResponseEntity<PlacePaymentResultResponse> place(@QueueAuth ValidQueueToken queueToken,
                                                            @RequestBody @Valid PlacePaymentRequest request
    ) {
        PlacePaymentResult paymentResult = paymentUseCase.place(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(PlacePaymentResultResponse.from(paymentResult));
    }
}
