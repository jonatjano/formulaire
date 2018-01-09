package iut.algo.form.job.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
  * @author Team Infotik
  * @version 2018-01-08
  */
public class SimpleErrorHandler implements ErrorHandler {

	/**
	  * permet de lancer un warning suite à l'exception levée
	  * @param exception exception relevée
	  * @throws {@link SAXException}
	  */
	public void warning(SAXParseException e) throws SAXException {
        System.out.println("WARNING : " + e.getMessage() + " sur la ligne " + (e.getLineNumber() - 2));
    }

	/**
	  * permet de lancer une erreur suite à l'exception levée
	  * @param exception exception relevée
	  * @throws {@link SAXException}
	  */
    public void error(SAXParseException e) throws SAXException {
        System.out.println("ERROR : " + e.getMessage() + " sur la ligne " + (e.getLineNumber() - 2));
        throw e;
    }

	/**
	  * permet de lancer une erreur fatale suite à l'exception levée
	  * @param exception exception relevée
	  * @throws {@link SAXException}
	  */
    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("FATAL ERROR : " + e.getMessage() + " sur la ligne " + (e.getLineNumber() - 2));
        throw e;
    }
}