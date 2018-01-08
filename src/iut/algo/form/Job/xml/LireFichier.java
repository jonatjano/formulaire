package iut.algo.form.job.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ReadFile
{
	
	public static void read(String file)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try
		{
			//Méthode qui permet d'activer la vérification du fichier
			factory.setValidating(true);

			DocumentBuilder builder = factory.newDocumentBuilder();

			//création de notre objet d'erreurs
			ErrorHandler errHandler = new SimpleErrorHandler();
			//Affectation de notre objet au document pour interception des erreurs éventuelles
			builder.setErrorHandler(errHandler);
			File fileXML = new File(file);

			//On rajoute un bloc de capture
			//pour intercepter les erreurs au cas où il y en a

			try
			{
				Document xml = builder.parse(fileXML);
				Element root = xml.getDocumentElement();
				
				Element window = (Element) root.getFirstChild();
				
				Frame.CreateFrame(window);
			}
			catch (SAXParseException e){}

		} 
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}
}