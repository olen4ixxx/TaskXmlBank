package io.olen4ixxx.bank.builder;

import io.olen4ixxx.bank.exception.CustomBankException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankBuilderFactory {
    private static final Logger logger = LogManager.getLogger();

    private enum TypeParser {
        SAX, STAX, DOM
    }

    private BankBuilderFactory() {
    }

    public static AbstractBankBuilder createStudentBuilder(String typeParser) throws CustomBankException {
        logger.info("AbstractBankBuilder : createStudentBuilder({})", typeParser);
        String typeToEnum = typeParser.toUpperCase();
        if (!typeToEnum.equals(TypeParser.DOM.toString())
                && !typeToEnum.equals(TypeParser.SAX.toString())
                && !typeToEnum.equals(TypeParser.STAX.toString())) {
            throw new CustomBankException("Invalid parser type");
        }
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM -> {
                return new DomBankBuilder();
            }
            case STAX -> {
                return new StaxBankBuilder();
            }
            case SAX -> {
                return new SaxBankBuilder();
            }
            default -> throw new CustomBankException("Invalid parser type");
        }
    }
}
