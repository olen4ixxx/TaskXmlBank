package io.olen4ixxx.bank.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class BankXmlErrorHandler implements ErrorHandler {
    private static final Logger logger = LogManager.getLogger();

    public void warning(SAXParseException e) {
        logger.warn(getLineColumnNumber(e) + "-" + e.getMessage());
    }
    public void error(SAXParseException e) {
        logger.warn(getLineColumnNumber(e) + "-" + e.getMessage());
    }
    public void fatalError(SAXParseException e) {
        logger.fatal(getLineColumnNumber(e) + " - " + e.getMessage());
    }
    private String getLineColumnNumber(SAXParseException e) {
        // determine line and position of error
        return e.getLineNumber() + " : " + e.getColumnNumber();
    }
}
