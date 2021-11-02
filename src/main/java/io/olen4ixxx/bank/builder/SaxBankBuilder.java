package io.olen4ixxx.bank.builder;

import io.olen4ixxx.bank.exception.CustomBankException;
import io.olen4ixxx.bank.validator.BankXmlErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;

public class SaxBankBuilder extends AbstractBankBuilder {
    private static final Logger logger = LogManager.getLogger();
    private final BankHandler handler;
    private final XMLReader reader;

    SaxBankBuilder() throws CustomBankException {
        handler = new BankHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            reader = parser.getXMLReader();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new BankXmlErrorHandler());
        } catch (SAXException | ParserConfigurationException e) {
            logger.error("Unable to configure SAX parser", e);
            throw new CustomBankException("Unable to configure SAX parser", e);
        }
    }

    @Override
    public void buildSetAccounts(String xmlFilePath) throws CustomBankException {
        logger.info("SaxBankBuilder: buildSetAccounts({})", xmlFilePath);
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(xmlFilePath);
        try {
            reader.parse(String.valueOf(resource));
        } catch (IOException e) {
            logger.error(String.format("Unable to read XML file %s", xmlFilePath), e);
            throw new CustomBankException(String.format("Unable to read XML file %s", xmlFilePath), e);
        } catch (SAXException e) {
            logger.error(String.format("Unable to parse XML file %s", xmlFilePath), e);
            throw new CustomBankException(String.format("Unable to parse XML file %s", xmlFilePath), e);
        }
        accounts = handler.getAccounts();
    }
}
