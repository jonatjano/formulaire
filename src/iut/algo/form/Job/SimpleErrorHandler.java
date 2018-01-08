package iut.algo.form.job.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
  * @author Team Infotik
  * @version 2018-01-08
  */
public class SimpleErrorHandler implements ErrorHandler {
<<<<<<< HEAD:src/iut/algo/form/Job/SimpleErrorHandler.java
    public void warning(SAXParseException e) throws SAXException {
		System.out.println("WARNING : " + e.getMessage() + " sur la ligne " + (e.getLineNumber() - 2));
        throw e;
=======
	/**
	  * permet de lancer un warning suite à l'exception levée
	  * @param exception exception relevée
	  * @throws {@link SAXException}
	  */
	public void warning(SAXParseException e) throws SAXException {
        System.out.println("WARNING : " + e.getMessage());
>>>>>>> 4d5a73345f6812cf40c8361f152d9a02213d7731:src/iut/algo/form/Job/xml/SimpleErrorHandler.java
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
