package io.olen4ixxx.bank.main;

import io.olen4ixxx.bank.builder.AbstractBankBuilder;
import io.olen4ixxx.bank.builder.BankXmlAttribute;
import io.olen4ixxx.bank.builder.BankXmlTag;
import io.olen4ixxx.bank.entity.Account;
import io.olen4ixxx.bank.entity.CheckingAccount;
import io.olen4ixxx.bank.entity.Deposit;
import io.olen4ixxx.bank.entity.BankCurrency;
import io.olen4ixxx.bank.entity.PaymentSystem;
import io.olen4ixxx.bank.exception.CustomBankException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

public class Staxxx extends AbstractBankBuilder { //todo
    private static final Logger logger = LogManager.getLogger();
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';
    private static final String DEPO = "deposit";
    private final XMLInputFactory inputFactory;
    private Account currentAccount;

    public Staxxx() {
        inputFactory = XMLInputFactory.newInstance();
        accounts = new HashSet<>();
    }

    public void buildSetAccounts(String xmlFilePath) throws CustomBankException {
        logger.info("StaxBankBuilder: buildSetAccounts(xmlFilePath:{})", xmlFilePath);
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(xmlFilePath)) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            // StAX parsing
            while (reader.hasNext()) {
                int type = reader.next();
                logger.info("{})type({})",1, type); //fixme
                logger.info("{})INFO({})",1, reader.getLocation()); //fixme
                if (type == XMLStreamConstants.START_ELEMENT) {
                    logger.info("{})START_ELEMENT={}",2, XMLStreamConstants.START_ELEMENT); //fixme
                    name = reader.getLocalName();
                    logger.info("{})name:{}",3, name); //fixme
                    if (name.equals(BankXmlTag.CHECKING_ACCOUNT.toString())
                            || name.equals(BankXmlTag.DEPOSIT.toString())) {
                        logger.info("{})if (name.equals",4); //fixme
                        currentAccount = name.equals(BankXmlTag.DEPOSIT.toString())
                                ? new Deposit() : new CheckingAccount();
                        accounts.add(buildAccount(reader));
                    }
                }
            }
        } catch (XMLStreamException e) {
            logger.error(String.format("Unable to parse XML file %s", xmlFilePath), e);
            throw new CustomBankException(String.format("Unable to parse XML file %s", xmlFilePath), e);
        } catch (IOException e) {
            logger.error(String.format("Unable to read XML file %s", xmlFilePath), e);
            throw new CustomBankException(String.format("Unable to read XML file %s", xmlFilePath), e);
        }
    }

    private Account buildAccount(XMLStreamReader reader) throws XMLStreamException {
        logger.info("{})buildAccount({}):{}",5, reader.getLocalName(), reader.getAttributeValue(0)); //fixme
        // todo null check
        currentAccount.setId(reader.getAttributeValue(null, BankXmlAttribute.ACCOUNT_ID.toString()));
        currentAccount.setOccupation(reader.getAttributeValue(null, BankXmlAttribute.OCCUPATION.toString()));
        String localName;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    localName = reader.getLocalName();
                    switch (BankXmlTag.valueOf(toEnumName(localName))) {
                        case BANK_NAME -> currentAccount.setBankName(getXMLText(reader));
                        case DEPOSITOR_NAME -> currentAccount.setDepositorName(getXMLText(reader));
                        case CURRENCY -> {
                            String enumName = toEnumName(getXMLText(reader));
                            currentAccount.setCurrency(BankCurrency.valueOf(enumName));
                        }
                        case REGISTRATION_DATE -> {
                            LocalDate date = LocalDate.parse(getXMLText(reader));
                            currentAccount.setRegistrationDate(date);
                        }
                        case AMOUNT -> {
                            double amount = Double.parseDouble(getXMLText(reader));
                            currentAccount.setAmount(amount);
                        }
                        case PAYMENT_SYSTEM -> {
                            if (currentAccount instanceof Deposit) break; //todo
                            CheckingAccount acc = (CheckingAccount) currentAccount;
                            String enumName = toEnumName(getXMLText(reader));
                            acc.setPaymentSystem(PaymentSystem.valueOf(enumName));
                        }
                        case CASHBACK -> {
                            if (currentAccount instanceof Deposit) break; //todo
                            CheckingAccount acc = (CheckingAccount) currentAccount;
                            int cashback = Integer.parseInt(getXMLText(reader));
                            acc.setCashback(cashback);
                        }
                        case PROFITABILITY -> {
                            if (currentAccount instanceof CheckingAccount) break; //todo
                            Deposit deposit = (Deposit) currentAccount;
                            int profitability = Integer.parseInt(getXMLText(reader));
                            deposit.setProfitability(profitability);
                        }
                        case DEPOSIT_TERM -> {
                            if (currentAccount instanceof CheckingAccount) break; //todo
                            Deposit deposit = (Deposit) currentAccount;
                            int depositTerm = Integer.parseInt(getXMLText(reader));
                            deposit.setDepositTerm(depositTerm);
                        }
                        case CALLABLE -> {
                            if (currentAccount instanceof CheckingAccount) break; //todo
                            Deposit deposit = (Deposit) currentAccount;
                            boolean callable = Boolean.parseBoolean(getXMLText(reader));
                            deposit.setCallable(callable);
                        }
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    localName = reader.getLocalName();
                    if (BankXmlTag.valueOf(toEnumName(localName)) == BankXmlTag.CHECKING_ACCOUNT
                            || BankXmlTag.valueOf(toEnumName(localName)) == BankXmlTag.DEPOSIT) {
                        return currentAccount;
                    }
                }
            }
        }
//        throw new XMLStreamException("Unknown element");
        return currentAccount;
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private String toEnumName(String tagName) {
        return tagName.strip().replace(HYPHEN, UNDERSCORE).toUpperCase();
    }
}
