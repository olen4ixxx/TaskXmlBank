package io.olen4ixxx.bank.entity;

import java.time.LocalDate;
import java.util.Objects;

public class CheckingAccount extends Account {
    private String paymentSystem;
    private int cashback;

    public CheckingAccount() {
    }

    public CheckingAccount(String id, String occupation, String bankName, String depositorName,
                           LocalDate date, double amount, String paymentSystem, int cashback) {
        super(id, occupation, bankName, depositorName, date, amount);
        this.paymentSystem = paymentSystem;
        this.cashback = cashback;
    }

    public String getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public int getCashback() {
        return cashback;
    }

    public void setCashback(int cashback) {
        this.cashback = cashback;
    }

    @Override
    public boolean equals(Object o) { //todo
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CheckingAccount that = (CheckingAccount) o;
        return cashback == that.cashback && Objects.equals(paymentSystem, that.paymentSystem);
    }

    @Override
    public int hashCode() { //todo
        return Objects.hash(super.hashCode(), paymentSystem, cashback);
    }

    @Override
    public String toString() { //todo
        return "CheckingAccount{" +
                "paymentSystem='" + paymentSystem + '\'' +
                ", cashback=" + cashback +
                '}';
    }
}
