package io.olen4ixxx.bank.main;

import io.olen4ixxx.bank.entity.PaymentSystem;
import io.olen4ixxx.bank.exception.CustomBankException;
import io.olen4ixxx.bank.validator.BankSchemaValidator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws SAXException, IOException, CustomBankException {

//        BankHandler handler = new BankHandler();
//        XMLReader reader = XMLReaderFactory.createXMLReader();
//        reader.setContentHandler(handler);
//        reader.parse("src/main/resources/files/deposit.xml");
//
//        PaymentSystem paymentSystem = PaymentSystem.VISA;
//        System.out.println(paymentSystem);

        BankSchemaValidator validator = new BankSchemaValidator();
        System.out.println(validator.validateXml("files/deposit.xml", "files/deposit.xsd"));
    }
}
