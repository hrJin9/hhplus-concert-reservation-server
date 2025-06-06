package kr.hhplus.be.server.domain.model;

import kr.hhplus.be.server.common.enums.PaymentMethod;

public class Payment {
    private Long id;
    private final Long userId;
    private final Long amount;
    private final PaymentMethod paymentMethod;

    private Payment(Long id, Long userId, Long amount, PaymentMethod paymentMethod) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public static Payment create(Long userId, Long amount, PaymentMethod paymentMethod) {
        return new Payment(
                null,
                userId,
                amount,
                paymentMethod
        );
    }

    public static Payment reconstitute(Long id, Long userId, Long amount, PaymentMethod paymentMethod) {
        return new Payment(
                id,
                userId,
                amount,
                paymentMethod
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
}
