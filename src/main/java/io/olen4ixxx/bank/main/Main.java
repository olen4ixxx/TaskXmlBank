package io.olen4ixxx.bank.main;

import io.olen4ixxx.bank.builder.AbstractBankBuilder;
import io.olen4ixxx.bank.builder.BankBuilderFactory;
import io.olen4ixxx.bank.builder.StaxBankBuilder;
import io.olen4ixxx.bank.exception.CustomBankException;
import io.olen4ixxx.bank.validator.BankSchemaValidator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws SAXException, IOException, CustomBankException, ParserConfigurationException {

//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        SAXParser parser = factory.newSAXParser();
//        XMLReader xmlReader = parser.getXMLReader();

//        BankHandler handler = new BankHandler();
//        XMLReader reader = XMLReaderFactory.createXMLReader();
//        reader.setContentHandler(handler);
//        reader.parse("src/main/resources/files/deposit.xml");
//
//        PaymentSystem paymentSystem = PaymentSystem.VISA;
//        System.out.println(paymentSystem);

        BankSchemaValidator validator = new BankSchemaValidator();
        System.out.println(validator.validateXml("files/deposit.xml", "files/deposit.xsd"));

//        AbstractBankBuilder builder = BankBuilderFactory.createStudentBuilder("DOM");
////        DomBankBuilder builder = new DomBankBuilder();
////        SaxBankBuilder builder = new SaxBankBuilder();
////        StaxBankBuilder builder = new StaxBankBuilder();
////        StaxBankBuilder builder = new StaxBankBuilder();
//        builder.buildSetAccounts("files/deposit.xml");
//        for (var a:builder.getAccounts()) {
//            System.out.println(a);
//        }
//        System.out.println(builder.getAccounts().size());
    }
}
