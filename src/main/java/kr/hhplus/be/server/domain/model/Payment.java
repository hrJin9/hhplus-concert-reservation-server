package kr.hhplus.be.server.domain.model;

import kr.hhplus.be.server.common.enums.PaymentMethod;
import kr.hhplus.be.server.common.enums.PaymentStatus;

public class Payment {
    private Long id;
    private final Long userId;
    private final Long amount;
    private final PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    private Payment(Long id, Long userId, Long amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public static Payment create(Long userId, Long amount, PaymentMethod paymentMethod) {
        return new Payment(
                null,
                userId,
                amount,
                paymentMethod,
                PaymentStatus.WAITING
        );
    }

    public static Payment reconstitute(Long id, Long userId, Long amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        return new Payment(
                id,
                userId,
                amount,
                paymentMethod,
                paymentStatus
        );
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void success() {
        this.paymentStatus = PaymentStatus.SUCCESS;
    }

    public void failed() {
        this.paymentStatus = PaymentStatus.FAILED;
    }
}
