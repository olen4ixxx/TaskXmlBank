package io.olen4ixxx.bank.entity;

public enum PaymentSystem {
    VISA("Visa"),
    MASTERCARD("MasterCard"),
    UNIONPAY("UnionPay");

    private String paymentSystem;

    PaymentSystem(String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public String getPaymentSystem() {
        return paymentSystem;
    }

    @Override
    public String toString() {
        return String.format("PaymentSystem{%s}", paymentSystem);
    }
}
