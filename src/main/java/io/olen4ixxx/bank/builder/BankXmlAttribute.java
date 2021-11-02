package io.olen4ixxx.bank.builder;

public enum BankXmlAttribute {
    ACCOUNT_ID,
    OCCUPATION;

    @Override
    public String toString() {
        return name().replace("_", "-").toLowerCase();
    }
}
