package io.olen4ixxx.bank.entity;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Account {
    private String id;
    private String occupation;
    private String bankName;
    private String depositorName;
    private LocalDate date;
    private double amount;

    public Account() { //todo
        //default?
    }

    public Account(String id, String occupation, String bankName, String depositorName, LocalDate date, double amount) {
        this.id = id;
        this.occupation = occupation;
        this.bankName = bankName;
        this.depositorName = depositorName;
        this.date = date;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDepositorName() {
        return depositorName;
    }

    public void setDepositorName(String depositorName) {
        this.depositorName = depositorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) { //todo
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.amount, amount) == 0 && Objects.equals(id, account.id) && Objects.equals(occupation, account.occupation) && Objects.equals(bankName, account.bankName) && Objects.equals(depositorName, account.depositorName) && Objects.equals(date, account.date);
    }

    @Override
    public int hashCode() { //todo
        return Objects.hash(id, occupation, bankName, depositorName, date, amount);
    }

    @Override
    public String toString() {
        return String.format("Account{id=%s, occupation=%s, bankName=%s, depositorName=%s, date=%s, amount=%s},",
                id, occupation, bankName, depositorName, date, amount);
    }
}
