package io.olen4ixxx.bank.validator;

import io.olen4ixxx.bank.exception.CustomBankException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.net.URL;

public class BankSchemaValidator {
    private static final Logger logger = LogManager.getLogger();

    public boolean validateXml(String stringPathXml, String stringPathXsd) throws CustomBankException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceXsd = classLoader.getResource(stringPathXsd);
        URL resourceXml = classLoader.getResource(stringPathXml);
        if (resourceXsd == null) {
            logger.error("File is not found ({})", stringPathXsd);
            throw new CustomBankException("File is not found");
        }
        BankXmlErrorHandler handler = new BankXmlErrorHandler();
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory schemaFactory = SchemaFactory.newInstance(language);
        try {
            Schema schema = schemaFactory.newSchema(resourceXsd);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(String.valueOf(resourceXml));
            validator.validate(source);
        } catch (IOException e) {
            logger.error("Check the file ()", e);
            throw new CustomBankException("Check the file", e);
        } catch (SAXParseException e) {
            handler.error(e);
            logger.error("File is not valid", e);
            return false;
        } catch (SAXException e) {
            logger.error("File is not valid", e);
            return false;
        }
        return true;
    }
}
