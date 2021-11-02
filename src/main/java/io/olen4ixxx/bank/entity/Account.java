package io.olen4ixxx.bank.entity;

import java.time.LocalDate;

public abstract class Account {
    private String id;
    private String occupation;
    private String bankName;
    private String depositorName;
    private BankCurrency currency;
    private LocalDate registrationDate;
    private double amount;

    public static final String DEFAULT_OCCUPATION = "IT";

    protected Account() {
        occupation = DEFAULT_OCCUPATION;
    }

    protected Account(String id, String occupation, String bankName, String depositorName,
                      BankCurrency currency, LocalDate registrationDate, double amount) {
        this.id = id;
        this.occupation = occupation;
        this.bankName = bankName;
        this.depositorName = depositorName;
        this.currency = currency;
        this.registrationDate = registrationDate;
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

    public BankCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(BankCurrency currency) {
        this.currency = currency;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Double.compare(account.amount, amount) == 0
                && id.equals(account.id)
                && occupation.equals(account.occupation)
                && bankName.equals(account.bankName)
                && depositorName.equals(account.depositorName)
                && currency == account.currency
                && registrationDate.equals(account.registrationDate);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + stringHash(id);
        result = prime * result + stringHash(occupation);
        result = prime * result + stringHash(bankName);
        result = prime * result + stringHash(depositorName);
        result = prime * result + currency.hashCode();
        result = prime * result + dateHash(registrationDate);
        long bits = Double.doubleToLongBits(amount);
        result = prime * result + (int) (bits ^ (bits >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "Account{id=%s, occupation=%s, bankName=%s, depositorName=%s, currency=%s, registrationDate=%s, amount=%s},",
                id, occupation, bankName, depositorName, currency, registrationDate, amount);
    }

    private int stringHash(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int hash = 0;
        char[] array = s.toCharArray();
        for (int i = 0; i < array.length; i++) {
            hash += Math.pow(31, array.length - i - 1.0) * array[i];
        }
        return hash;
    }

    private int dateHash(LocalDate date) {
        if (date == null) {
            return 0;
        }
        int yearValue = date.getYear();
        int monthValue = date.getMonthValue();
        int dayValue = date.getDayOfMonth();
        return (yearValue & 0xFFFFF800) ^ ((yearValue << 11) + (monthValue << 6) + (dayValue));
    }
}
