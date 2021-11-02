package io.olen4ixxx.bank.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class BankXmlErrorHandler implements ErrorHandler {
    private static final Logger logger = LogManager.getLogger();
    private static final String LOG_FORMAT = "{}-{}";

    public void warning(SAXParseException e) {
        logger.warn(LOG_FORMAT, getLineColumnNumber(e), e.getMessage());
    }

    public void error(SAXParseException e) {
        logger.error(LOG_FORMAT, getLineColumnNumber(e), e.getMessage());
    }

    public void fatalError(SAXParseException e) {
        logger.fatal(LOG_FORMAT, getLineColumnNumber(e), e.getMessage());
    }

    private String getLineColumnNumber(SAXParseException e) {
        return String.format("%d : %d", e.getLineNumber(), e.getColumnNumber());
    }
}
