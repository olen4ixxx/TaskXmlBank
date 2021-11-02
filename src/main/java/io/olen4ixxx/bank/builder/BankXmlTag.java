package io.olen4ixxx.bank.builder;

public enum BankXmlTag {
    ACCOUNTS,
    CHECKING_ACCOUNT,
    DEPOSIT,
    BANK_NAME,
    DEPOSITOR_NAME,
    CURRENCY,
    REGISTRATION_DATE,
    AMOUNT,
    PAYMENT_SYSTEM,
    CASHBACK,
    PROFITABILITY,
    DEPOSIT_TERM,
    CALLABLE;

    @Override
    public String toString() {
        return name().replace("_", "-").toLowerCase();
    }
}
