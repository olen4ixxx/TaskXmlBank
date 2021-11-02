package io.olen4ixxx.bank.entity;

import java.time.LocalDate;

public class CheckingAccount extends Account {
    private PaymentSystem paymentSystem;
    private int cashback;

    public CheckingAccount() {
    }

    public CheckingAccount(String id, String occupation, String bankName, String depositorName, BankCurrency currency,
                           LocalDate date, double amount, PaymentSystem paymentSystem, int cashback) {
        super(id, occupation, bankName, depositorName, currency, date, amount);
        this.paymentSystem = paymentSystem;
        this.cashback = cashback;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public int getCashback() {
        return cashback;
    }

    public void setCashback(int cashback) {
        this.cashback = cashback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CheckingAccount account = (CheckingAccount) o;
        return cashback == account.cashback && paymentSystem == account.paymentSystem;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + paymentSystem.hashCode();
        result = prime * result + cashback;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Account{id=%s, occupation=%s, bankName=%s, depositorName=%s, currency=%s, " +
                        "registrationDate=%s, amount=%s, paymentSystem=%s, cashback=%s},",
                getId(), getOccupation(), getBankName(), getDepositorName(), getCurrency(),
                getRegistrationDate(), getAmount(), paymentSystem, cashback);
    }
}
