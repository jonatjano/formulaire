package iut.algo.form.job.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {
    public void warning(SAXParseException e) throws SAXException {
		System.out.println("WARNING : " + e.getMessage() + " sur la ligne " + (e.getLineNumber() - 2));
        throw e;
    }

    public void error(SAXParseException e) throws SAXException {
        System.out.println("ERROR : " + e.getMessage() + " sur la ligne " + (e.getLineNumber() - 2));
        throw e;
    }

    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("FATAL ERROR : " + e.getMessage() + " sur la ligne " + (e.getLineNumber() - 2));
        throw e;
    }
}
