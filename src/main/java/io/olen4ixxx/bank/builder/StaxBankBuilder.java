package io.olen4ixxx.bank.builder;

import io.olen4ixxx.bank.entity.*;
import io.olen4ixxx.bank.exception.CustomBankException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;

public class StaxBankBuilder extends AbstractBankBuilder {
    private static final Logger logger = LogManager.getLogger();
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';
    private static final String CA = "checking-account";
    private static final String DEPO = "deposit";
    private final XMLInputFactory inputFactory;
    private Account account;
    private XMLEventReader reader;
    private XMLEvent event;

    StaxBankBuilder() {
        inputFactory = XMLInputFactory.newInstance();
        accounts = new HashSet<>();
    }

    public void buildSetAccounts(String xmlFilePath) throws CustomBankException {
        logger.info("StaxBankBuilder: buildSetAccounts(xmlFilePath:{})", xmlFilePath);
        String fileName = parseFileName(xmlFilePath);
        try {
            InputStream stream = new FileInputStream(fileName);
            reader = inputFactory.createXMLEventReader(stream);
            while (reader.hasNext()) {
                event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    onStartSetAttributes(startElement);
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String localName = endElement.getName().getLocalPart();
                    if (localName.equals(DEPO) || localName.equals(CA)) {
                        accounts.add(account);
                    }
                }
            }
        } catch (IOException e) {
            logger.error(String.format("Unable to read XML file %s", xmlFilePath), e);
            throw new CustomBankException(String.format("Unable to read XML file %s", xmlFilePath), e);
        } catch (XMLStreamException e) {
            logger.error(String.format("Unable to parse XML file %s", xmlFilePath), e);
            throw new CustomBankException(String.format("Unable to parse XML file %s", xmlFilePath), e);
        }
    }

    private String toEnumName(String tagName) {
        return tagName.strip().replace(HYPHEN, UNDERSCORE).toUpperCase();
    }

    private String parseFileName(String xmlFilePath) throws CustomBankException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(xmlFilePath);
        if (resource == null) {
            logger.error("File is not found ({})", xmlFilePath);
            throw new CustomBankException("File is not found");
        }
        URI uri;
        try {
            uri = resource.toURI();
        } catch (URISyntaxException e) {
            logger.error("Wrong file path ({})", xmlFilePath);
            throw new CustomBankException("Wrong file path", e);
        }
        Path path = Path.of(uri);
        return String.valueOf(path);
    }

    private void onStartSetAttributes(StartElement startElement) throws XMLStreamException {
        String localName = startElement.getName().getLocalPart();
        switch (localName) {
            case DEPO, CA -> {
                account = localName.equals(DEPO) ? new Deposit() : new CheckingAccount();
                Attribute id = startElement.getAttributeByName(new QName("id"));
                account.setId(id.getValue());
                Attribute occupation = startElement.getAttributeByName(new QName("occupation"));
                if (occupation != null) {
                    account.setOccupation(occupation.getValue());
                } else {
                    account.setOccupation(Account.DEFAULT_OCCUPATION);
                }
            }
            case ("bank-name") -> {
                event = reader.nextEvent();
                account.setBankName(event.asCharacters().getData());
            }
            case ("depositor-name") -> {
                event = reader.nextEvent();
                account.setDepositorName(event.asCharacters().getData());
            }
            case ("currency") -> {
                event = reader.nextEvent();
                String currency = toEnumName(event.asCharacters().getData());
                account.setCurrency(BankCurrency.valueOf(currency));
            }
            case ("registration-date") -> {
                event = reader.nextEvent();
                LocalDate date = LocalDate.parse(event.asCharacters().getData());
                account.setRegistrationDate(date);
            }
            case ("amount") -> {
                event = reader.nextEvent();
                double amount = Double.parseDouble(event.asCharacters().getData());
                account.setAmount(amount);
            }
            case ("profitability") -> {
                if (account instanceof Deposit deposit) {
                    event = reader.nextEvent();
                    int profitability = Integer.parseInt(event.asCharacters().getData());
                    deposit.setProfitability(profitability);
                }
            }
            case ("deposit-term") -> {
                if (account instanceof Deposit deposit) {
                    event = reader.nextEvent();
                    int term = Integer.parseInt(event.asCharacters().getData());
                    deposit.setDepositTerm(term);
                }
            }
            case ("callable") -> {
                if (account instanceof Deposit deposit) {
                    event = reader.nextEvent();
                    boolean callable = Boolean.parseBoolean(event.asCharacters().getData());
                    deposit.setCallable(callable);
                }
            }
            case ("payment-system") -> {
                if (account instanceof CheckingAccount acc) {
                    event = reader.nextEvent();
                    String system = toEnumName(event.asCharacters().getData());
                    acc.setPaymentSystem(PaymentSystem.valueOf(system));
                }
            }
            case ("cashback") -> {
                if (account instanceof CheckingAccount acc) {
                    event = reader.nextEvent();
                    int cashback = Integer.parseInt(event.asCharacters().getData());
                    acc.setCashback(cashback);
                }
            }
        }
    }
}
