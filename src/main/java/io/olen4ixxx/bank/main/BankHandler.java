package io.olen4ixxx.bank.main;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class BankHandler extends DefaultHandler {
    @Override
    public void startDocument() {
        System.out.println("Start");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String s = localName;
        for (int i = 0; i < attributes.getLength(); i++) {
            s += " " + attributes.getLocalName(i) + "=" + attributes.getValue(i);
        }
        System.out.println(s.trim());
    }
}
