package io.olen4ixxx.bank.exception;

public class CustomBankException extends Exception {
    public CustomBankException(String message) {
        super(message);
    }

    public CustomBankException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomBankException() {
    }

    public CustomBankException(Throwable cause) {
        super(cause);
    }
}
