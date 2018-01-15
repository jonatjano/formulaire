package iut.algo.form.job;

import iut.algo.form.FormController;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Gère les erreurs de lecture du xml
 * @author Team Infotik
 * @version 2018-01-08
 */
public class SimpleErrorHandler implements ErrorHandler {

	/**
	  * Affiche un avertissement suite à une exception
	  * @param exception Exception levée
	  * @throws {@link SAXException}
	  */
	public void warning(SAXParseException exception)
	throws SAXException
	{
        FormController.showMessage("WARNING", exception.getMessage() + " sur la ligne " + exception.getLineNumber());
    }

	/**
	  * Affiche une erreur suite à une exception
	  * @param exception Exception levée
	  * @throws {@link SAXException}
	  */
    public void error(SAXParseException exception)
    throws SAXException
    {
        FormController.showMessage("ERROR", exception.getMessage() + " sur la ligne " + exception.getLineNumber());
        throw exception;
    }

	/**
	  * permet de lancer une erreur fatale suite à l'exception levée
	  * @param exception Exception levée
	  * @throws {@link SAXException}
	  */
    public void fatalError (SAXParseException exception)
    throws SAXException
    {
        FormController.showMessage("FATAL ERROR", exception.getMessage() + " sur la ligne " + exception.getLineNumber());
        throw exception;
    }
}
