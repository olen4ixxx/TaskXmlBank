package io.olen4ixxx.bank.builder;

import io.olen4ixxx.bank.exception.CustomBankException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BankBuilderFactoryTest {

    @BeforeMethod
    public void setUp() {
    }

    @Test(expectedExceptions = CustomBankException.class)
    public void testCreateStudentBuilderException() throws CustomBankException {
        BankBuilderFactory.createStudentBuilder("WRONG_NAME");
    }
}