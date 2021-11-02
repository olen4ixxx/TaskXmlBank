package io.olen4ixxx.bank.builder;

import io.olen4ixxx.bank.entity.Account;
import io.olen4ixxx.bank.exception.CustomBankException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class StaxBankBuilderTest {
    AbstractBankBuilder builder;

    @BeforeMethod
    public void setUp() throws CustomBankException {
        builder = BankBuilderFactory.createStudentBuilder("STAX");
        builder.buildSetAccounts("depositTest.xml");
    }

    @Test(timeOut = 5000)
    public void testBuildSetAccountsNumberOfElements() {
        assertTrue(builder.getAccounts().size() == 16);
    }

    @Test(timeOut = 5000)
    public void testBuildSetAccountsId() {
        final String regexId = "[A-Z]{2}[0-9]{2}";
        Set<Account> set = builder.getAccounts();
        boolean flag = true;
        Iterator<Account> iterator = set.iterator();
        while (iterator.hasNext()) {
            Account account = iterator.next();
            if (!account.getId().matches(regexId)) {
                flag = false;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test(timeOut = 5000)
    public void testBuildSetAccounts() {
        Set<Account> set = builder.getAccounts();
        boolean flag = true;
        Iterator<Account> iterator = set.iterator();
        while (iterator.hasNext()) {
            Account account = iterator.next();
            String occupation = account.getOccupation();
            if (!occupation.equals("IT") && !occupation.equals("Medicine") && !occupation.equals("Finance")) {
                flag = false;
                break;
            }
        }
        assertTrue(flag);
    }
}