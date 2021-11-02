package io.olen4ixxx.bank.builder;

import io.olen4ixxx.bank.entity.*;
import io.olen4ixxx.bank.exception.CustomBankException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;

public class DomBankBuilder extends AbstractBankBuilder {
    private static final Logger logger = LogManager.getLogger();
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';
    private static final String CA = "checking-account";
    private static final String DEPO = "deposit";
    private final DocumentBuilder docBuilder;
    private Account currentAccount;

    DomBankBuilder() throws CustomBankException {
        accounts = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new CustomBankException("Unable to configure DOM parser", e);
        }
    }

    public void buildSetAccounts(String xmlFilePath) throws CustomBankException {
        logger.info("DomBankBuilder: buildSetAccounts(xmlFilePath:{})", xmlFilePath);
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(xmlFilePath);
        Document doc;
        try {
            doc = docBuilder.parse(String.valueOf(resource));
            Element root = doc.getDocumentElement();
            NodeList accountList = root.getElementsByTagName(CA);
            for (int i = 0; i < accountList.getLength(); i++) {
                Element accountElement = (Element) accountList.item(i);
                currentAccount = new CheckingAccount();
                accounts.add(buildAccount(accountElement));
            }
            accountList = root.getElementsByTagName(DEPO);
            for (int i = 0; i < accountList.getLength(); i++) {
                Element accountElement = (Element) accountList.item(i);
                currentAccount = new Deposit();
                accounts.add(buildAccount(accountElement));
            }
        } catch (IOException e) {
            logger.error(String.format("Unable to read XML file %s", xmlFilePath), e);
            throw new CustomBankException(String.format("Unable to read XML file %s", xmlFilePath), e);
        } catch (SAXException e) {
            logger.error(String.format("Unable to parse XML file %s", xmlFilePath), e);
            throw new CustomBankException(String.format("Unable to parse XML file %s", xmlFilePath), e);
        }
    }

    private Account buildAccount(Element accountElement) {
        logger.info("buildAccount({})", accountElement);
        currentAccount.setId(accountElement.getAttribute("id"));
        String occupation = accountElement.getAttribute("occupation");
        if (!occupation.isEmpty()) {
            currentAccount.setOccupation(occupation);
        } else {
            currentAccount.setOccupation(Account.DEFAULT_OCCUPATION);
        }
        currentAccount.setBankName(getElementTextContent(accountElement, "bank-name"));
        currentAccount.setDepositorName(getElementTextContent(accountElement, "depositor-name"));
        String currency = toEnumName(getElementTextContent(accountElement, "currency"));
        currentAccount.setCurrency(BankCurrency.valueOf(currency));
        LocalDate registrationDate = LocalDate.parse(getElementTextContent(accountElement, "registration-date"));
        currentAccount.setRegistrationDate(registrationDate);
        double amount = Double.parseDouble(getElementTextContent(accountElement, "amount"));
        currentAccount.setAmount(amount);
        if (currentAccount instanceof CheckingAccount account) {
            String paymentSystem = toEnumName(getElementTextContent(accountElement, "payment-system"));
            account.setPaymentSystem(PaymentSystem.valueOf(paymentSystem));
            int cashback = Integer.parseInt(getElementTextContent(accountElement, "cashback"));
            account.setCashback(cashback);
        }
        if (currentAccount instanceof Deposit account) {
            int profitability = Integer.parseInt(getElementTextContent(accountElement, "profitability"));
            account.setProfitability(profitability);
            int depositTerm = Integer.parseInt(getElementTextContent(accountElement, "deposit-term"));
            account.setDepositTerm(depositTerm);
            boolean callable = Boolean.parseBoolean(getElementTextContent(accountElement, "callable"));
            account.setCallable(callable);
        }
        return currentAccount;
    }

    // get the text content of the tag
    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }

    private String toEnumName(String tagName) {
        return tagName.strip().replace(HYPHEN, UNDERSCORE).toUpperCase();
    }
}
