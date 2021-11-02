package io.olen4ixxx.bank.main;

import io.olen4ixxx.bank.validator.BankXmlErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class BankSaxParser {
    public void parse(String stringPathXml) { //todo delete
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(new BankHandler());
            reader.setErrorHandler(new BankXmlErrorHandler());
            reader.parse(stringPathXml);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
