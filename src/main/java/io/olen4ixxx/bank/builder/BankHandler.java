package io.olen4ixxx.bank.builder;

import io.olen4ixxx.bank.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class BankHandler extends DefaultHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';
    private static final String ATTR_ID_QNAME = "id";
    private final Set<Account> accounts;
    private final EnumSet<BankXmlTag> tagsWithText;
    private Account currentAccount;
    private BankXmlTag currentTagEnum;

    public BankHandler() {
        accounts = new HashSet<>();
        tagsWithText = EnumSet.range(BankXmlTag.BANK_NAME, BankXmlTag.CALLABLE);
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        logger.info("BankHandler: startElement(uri:{} qName:{} attributes:{})", uri, qName, attributes);
        String checkingAccountTag = BankXmlTag.CHECKING_ACCOUNT.toString();
        String depositTag = BankXmlTag.DEPOSIT.toString();
        if (checkingAccountTag.equals(qName) || depositTag.equals(qName)) {
            currentAccount = depositTag.equals(qName) ? new Deposit() : new CheckingAccount();
            for (int i = 0; i < attributes.getLength(); i++) {
                String attr = attributes.getValue(i);
                String attrName = attributes.getQName(i);
                if (attrName.equals(ATTR_ID_QNAME)) {
                    currentAccount.setId(attr);
                } else {
                    currentAccount.setOccupation(attr);
                }
            }
        } else {
            String name = toEnumName(qName);
            BankXmlTag tag = BankXmlTag.valueOf(name);
            if (tagsWithText.contains(tag)) {
                currentTagEnum = tag;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        logger.info("BankHandler: endElement(uri:{} qName:{})", uri, qName);
        String checkingAccountTag = BankXmlTag.CHECKING_ACCOUNT.toString();
        String depositTag = BankXmlTag.DEPOSIT.toString();
        if (checkingAccountTag.equals(qName) || depositTag.equals(qName)) {
            accounts.add(currentAccount);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        logger.info("BankHandler: characters(start:{} length:{})", start, length);
        if (currentTagEnum == null) {
            return;
        }
        String data = new String(ch, start, length);
        switch (currentTagEnum) {
            case BANK_NAME -> currentAccount.setBankName(data);
            case DEPOSITOR_NAME -> currentAccount.setDepositorName(data);
            case CURRENCY -> {
                String name = toEnumName(data);
                currentAccount.setCurrency(BankCurrency.valueOf(name));
            }
            case REGISTRATION_DATE -> currentAccount.setRegistrationDate(LocalDate.parse(data));
            case AMOUNT -> currentAccount.setAmount(Double.parseDouble(data));
            case PAYMENT_SYSTEM -> {
                CheckingAccount account = (CheckingAccount) currentAccount;
                String name = toEnumName(data);
                account.setPaymentSystem(PaymentSystem.valueOf(name));
            }
            case CASHBACK -> {
                CheckingAccount account = (CheckingAccount) currentAccount;
                account.setCashback(Integer.parseInt(data));
            }
            case PROFITABILITY -> {
                Deposit account = (Deposit) currentAccount;
                account.setProfitability(Integer.parseInt(data));
            }
            case DEPOSIT_TERM -> {
                Deposit account = (Deposit) currentAccount;
                account.setDepositTerm(Integer.parseInt(data));
            }
            case CALLABLE -> {
                Deposit account = (Deposit) currentAccount;
                account.setCallable(Boolean.parseBoolean(data));
            }
            default -> {
                logger.error("EnumConstantNotPresentException: {}, {}",
                        currentTagEnum.getDeclaringClass(), currentTagEnum.name());
                throw new EnumConstantNotPresentException(currentTagEnum.getDeclaringClass(), currentTagEnum.name());
            }
        }
        currentTagEnum = null;
    }

    private String toEnumName(String tagName) {
        return tagName.strip().replace(HYPHEN, UNDERSCORE).toUpperCase();
    }
}
