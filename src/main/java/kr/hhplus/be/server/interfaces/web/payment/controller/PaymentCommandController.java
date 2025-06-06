package kr.hhplus.be.server.interfaces.web.payment.controller;

import jakarta.validation.Valid;
import kr.hhplus.be.server.application.payment.dto.PaymentResult;
import kr.hhplus.be.server.application.payment.service.PaymentCommandService;
import kr.hhplus.be.server.interfaces.web.payment.request.CreatePaymentRequest;
import kr.hhplus.be.server.interfaces.web.payment.response.PaymentResponse;
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
    private final PaymentCommandService paymentCommandService;
    @PostMapping
    public ResponseEntity<PaymentResponse> placePayment(@QueueAuth ValidQueueToken queueToken,
                                                        @RequestBody @Valid CreatePaymentRequest request
    ) {
        PaymentResult paymentResult = paymentCommandService.placePayment(queueToken.userId(), request.toCommand());
        return ResponseEntity.ok(PaymentResponse.from(paymentResult));
    }

}
