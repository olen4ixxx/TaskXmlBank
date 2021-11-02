package io.olen4ixxx.bank.entity;

import java.time.LocalDate;

public class Deposit extends Account {
    private int profitability;
    private int depositTerm;
    private boolean callable;

    public Deposit() {
    }

    public Deposit(String id, String occupation, String bankName, String depositorName, BankCurrency currency,
                   LocalDate date, double amount, int profitability, int depositTerm, boolean callable) {
        super(id, occupation, bankName, depositorName, currency, date, amount);
        this.profitability = profitability;
        this.depositTerm = depositTerm;
        this.callable = callable;
    }

    public int getProfitability() {
        return profitability;
    }

    public void setProfitability(int profitability) {
        this.profitability = profitability;
    }

    public int getDepositTerm() {
        return depositTerm;
    }

    public void setDepositTerm(int depositTerm) {
        this.depositTerm = depositTerm;
    }

    public boolean isCallable() {
        return callable;
    }

    public void setCallable(boolean callable) {
        this.callable = callable;
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
        Deposit deposit = (Deposit) o;
        return profitability == deposit.profitability
                && depositTerm == deposit.depositTerm
                && callable == deposit.callable;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + profitability;
        result = prime * result + depositTerm;
        int callableHash = callable ? 1 : 0;
        result = prime * result + callableHash;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Account{id=%s, occupation=%s, bankName=%s, depositorName=%s, currency=%s, " +
                        "registrationDate=%s, amount=%s, profitability=%s, depositTerm=%s, callable=%s},",
                getId(), getOccupation(), getBankName(), getDepositorName(), getCurrency(),
                getRegistrationDate(), getAmount(), profitability, depositTerm, callable);
    }
}
