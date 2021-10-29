package io.olen4ixxx.bank.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Deposit extends Account {
    private int profitability;
    private int depositTerm;
    private boolean callable;

    public Deposit() {
    }

    public Deposit(String id, String occupation, String bankName, String depositorName,
                   LocalDate date, double amount, int profitability, int depositTerm, boolean callable) {
        super(id, occupation, bankName, depositorName, date, amount);
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
    public boolean equals(Object o) { //todo
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Deposit deposit = (Deposit) o;
        return profitability == deposit.profitability && depositTerm == deposit.depositTerm && callable == deposit.callable;
    }

    @Override
    public int hashCode() { //todo
        return Objects.hash(super.hashCode(), profitability, depositTerm, callable);
    }

    @Override
    public String toString() { //todo
        return "Deposit{" +
                "profitability=" + profitability +
                ", depositTerm=" + depositTerm +
                ", callable=" + callable +
                '}';
    }
}
