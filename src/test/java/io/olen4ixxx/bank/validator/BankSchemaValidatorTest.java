package io.olen4ixxx.bank.validator;

import io.olen4ixxx.bank.exception.CustomBankException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BankSchemaValidatorTest {
    BankSchemaValidator validator;

    @BeforeMethod
    public void setUp() {
        validator = new BankSchemaValidator();
    }

    @Test(timeOut = 5000)
    public void testValidateXml() throws CustomBankException {
        boolean flag = validator.validateXml("depositTest.xml", "depositTest.xsd");
        assertTrue(flag);
    }

    @Test(timeOut = 5000)
    public void testValidateXmlFileIsNotValid() throws CustomBankException {
        boolean flag = validator.validateXml("depositTestValidationFail.xml",
                "depositTestValidationFail.xsd");
        assertFalse(flag);
    }

    @Test(expectedExceptions = CustomBankException.class)
    public void testValidateXmlExceptionWrongName() throws CustomBankException {
        validator.validateXml("wrongName.xml", "wrongName.xsd");
    }
}