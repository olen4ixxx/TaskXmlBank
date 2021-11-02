package io.olen4ixxx.bank.builder;

import io.olen4ixxx.bank.entity.Account;
import io.olen4ixxx.bank.exception.CustomBankException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBankBuilder {

    protected Set<Account> accounts;

    protected AbstractBankBuilder() {
        accounts = new HashSet<>();
    }

    protected AbstractBankBuilder(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public abstract void buildSetAccounts(String xmlFilePath) throws CustomBankException;
}
