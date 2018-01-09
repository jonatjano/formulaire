package iut.algo.form;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import iut.algo.form.job.SimpleErrorHandler;
import iut.algo.form.view.Frame;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * classe permettant de controller les formulaires depuis des programmes exterieurs
 * @author Team Infotik
 * @version 2018-01-08
 */
public class FormController
{
	private static File dtdFile = createDtdFile();

	/**
	 * methode appellée par une classe externe au package permettant d'appeller tous les utilitaires nécessaire au formulaire
	 * @param filePath  Le chemin du fichier XML permettant de générer le formlaire
	 */
	public static void showForm(String filePath)
	{
		showForm(filePath, null);
	}

	/**
	 * methode appellée par une classe externe au package permettant d'appeller tous les utilitaires nécessaire au formulaire
	 * @param filePath  Le chemin du fichier XML permettant de générer le formlaire
	 *                  	Lu comme absolu sur classType est null
	 *                  	Lu comme relatif si classType n'est pas null
	 * @param classType La classe demandant le formulaire utilisée quand le filePath est un chemin relatif à la classe appellante
	 */
	public static void showForm(String filePath, Class classType)
	{
		// on recupere le fichier
		File xmlFile = new File(filePath);

		// on fait les verifications basique d'existence du fichier
		// s'il existe
		if (!xmlFile.exists())
		{
			showError("Le fichier entré n'existe pas");
			return;
		}
		// s'il possède bien une extension XML
		else if (!xmlFile.getName().toUpperCase().endsWith(".XML"))
		{
			showError("Le fichier entré ne correspond pas à un fichier XML");
			return;
		}


		try
		{
			// creer un fichier temporaire
			File xmlFileWithDTD = File.createTempFile("tmpFile", ".xml");
			xmlFileWithDTD.deleteOnExit();

			// on lit le fichier en cours
			PrintWriter pw = new PrintWriter(xmlFileWithDTD, "UTF-8");

			Scanner scan = new Scanner(xmlFile, "UTF-8");

			String file = "";
			while (scan.hasNext())
			{
				file += "\n" + scan.nextLine();
			}

			String finalFile = "<?xml version=\"1.0\" ?>\n";
			finalFile += "<!DOCTYPE form SYSTEM \"" + dtdFile.getAbsolutePath() + "\">\n";
			finalFile += file.substring( file.indexOf("<form")).replaceAll("[\t\n]",""); // a modifier
			pw.write( finalFile );

			pw.close();
			
			Element root = validXml(xmlFileWithDTD);
			
			System.out.println(finalFile);
			if(root == null)
			{

			}
			else
				Frame.createFrame( (Element) (root.getFirstChild()) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// le fichier est accepté, on l'envoi à la suite du traitement
		// parseXml(xmlFile);
	}

	/**
	 * cette méthode permet de savoir si le document XML respecte la dtd et écrit des erreurs dans le terminal sinon
	 * @param fileXML le fichier XML a vérifié
	 * @return boolean L'élément si le fichier valide, sinon null
	 */
	private static Element validXml (File fileXML)
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

			//On rajoute un bloc de capture
			//pour intercepter les erreurs au cas où il y en a

			try
			{
				Document xml = builder.parse(fileXML);
				Element root = xml.getDocumentElement();
				return root;
			}
			catch (SAXParseException e) {}
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
		return null;
	}

	/**
	 * Cette méthode permet de créer la dtd dans un fichier temporaire
	 * @return File le fichier dtd créé
	 */
	private static File createDtdFile()
	{
		try {
			File dtd = File.createTempFile("dtd", ".dtd");
			dtd.deleteOnExit();

			PrintWriter pw = new PrintWriter(dtd, "UTF-8");

			pw.write("\t\t\t<!ELEMENT form (fenetre|window)>\n");

			pw.write("\t\t\t\t<!ELEMENT fenetre (texte|menu|case|tableau|boutons)+>\n");
			pw.write("\t\t\t\t   <!ATTLIST fenetre longueur CDATA #REQUIRED\n");
			pw.write("\t\t\t\t   \t\t\t\t\t largeur  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t titre    CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t\t  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t\t  CDATA #REQUIRED>\n");
			pw.write("\t\t\t\t   <!ELEMENT texte EMPTY>\n");
			pw.write("\t\t\t\t      <!ATTLIST texte type  ( chaine | entier | double | booleen | caractere )  #REQUIRED\n");
			pw.write("\t\t\t\t\t  \t\t\t\t label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t id\t   ID\t #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t   CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t   CDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t<!ELEMENT menu (choix+)>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST menu type ( chaine | entier | double | booleen | caractere ) #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   id   ID\t  #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   x\tCDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   y\tCDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT choix EMPTY>\n");
			pw.write("\t\t\t\t\t\t\t<!ATTLIST choix label CDATA #REQUIRED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT case EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST case label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   id    ID\t   #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t   CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t   CDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t<!ELEMENT tableau EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST tableau label  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t  id\t ID\t   #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t  type   ( chaine | entier | double | booleen | caractere ) #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t  nb_lig CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t  nb_col CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t   CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t   CDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t<!ELEMENT boutons (bouton+)>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST boutons label  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t\t\tid\t\t CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t   CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t   CDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT bouton (#PCDATA)>\n");

			pw.write("\t\t\t\t<!ELEMENT window (text|dropdown|checkbox|array|buttons)+>\n");
			pw.write("\t\t\t\t   \t<!ATTLIST window length\tCDATA #REQUIRED\n");
			pw.write("\t\t\t\t   \t\t\t\t\t width \tCDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t title  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t\tCDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t\tCDATA #REQUIRED>\n");
			pw.write("\t\t\t\t   \t<!ELEMENT text (#PCDATA)>\n");
			pw.write("\t\t\t\t      \t<!ATTLIST text type  ( string | int | double | boolean | char )    #REQUIRED\n");
			pw.write("\t\t\t\t\t  \t\t\t\t   label CDATA \t#REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t   id\t ID\t \t#REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t   x\t CDATA \t#IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t   y\t CDATA \t#IMPLIED  >\n");
			pw.write("\t\t\t\t\t<!ELEMENT dropdown (choice+)>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST dropdown type ( string | int | double | boolean | char ) #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   id   ID\t  #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   x\tCDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   y\tCDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT choice EMPTY>\n");
			pw.write("\t\t\t\t\t\t\t<!ATTLIST choice label CDATA #REQUIRED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT checkbox EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST checkbox label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t   id    ID\t   #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t   CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t   CDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t<!ELEMENT array EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST array label  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t  id\t ID\t   #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t  type   ( string | int | double | boolean | char ) #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t  nb_lig CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t  nb_col CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t   CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t   CDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t<!ELEMENT buttons (button+)>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST buttons label  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t\t\t\tid\t\t CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t\t\t x\t   CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t\t\t y\t   CDATA #IMPLIED  >\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT button (#PCDATA)>\n");


			pw.close();

			return dtd;
		}
		catch (Exception e) {}
		return null;
	}

	/**
	 * Cette méthode permet d'afficher une erreur à l'écran avec le titre "Erreur"
	 * @param message message à afficher dans l'erreur
	 */
	private static void showError(String message)
	{
		showError("Erreur", message);
	}

	/**
	 * Cette méthode permet d'afficher une erreur à l'écran
	 * @param titre titre du message d'erreur
	 * @param message message à afficher dans l'erreur
	 */
	private static void showError( String titre , String message )
	{
		JOptionPane.showMessageDialog(null,message,titre,JOptionPane.ERROR_MESSAGE);
	}
}
