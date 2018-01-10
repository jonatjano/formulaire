package iut.algo.form;

import iut.algo.form.job.SimpleErrorHandler;
import iut.algo.form.view.Control;
import iut.algo.form.view.Frame;
import iut.algo.form.job.BaseType;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import java.util.Map;
import java.util.HashMap;
import org.xml.sax.SAXParseException;

/**
 * classe permettant de controller les formulaires depuis des programmes exterieurs
 * @author Team Infotik
 * @version 2018-01-08
 */
public class FormController
{
	/**
	 * le fichier dtd utilisé pour valider le xml
	 */
	private static File dtdFile = createDtdFile();
	/**
	 * permet de savoir si un formulaire est actuellement ouvert
	 */
	private static boolean windowIsOpen = false;
	/**
	 * la fenetre du dernier formulaire
	 */
	private static Frame frame;

	/**
	 * {@link Map} contennant les valeurs des différent champs de type {@link Integer}
	 */
	private static Map<String, Integer> intMap;
	/**
	 * {@link Map} contennant les valeurs des différent champs de type {@link String}
	 */
	private static Map<String, String> stringMap;
	/**
	 * {@link Map} contennant les valeurs des différent champs de type {@link Double}
	 */
	private static Map<String, Double> doubleMap;
	/**
	 * {@link Map} contennant les valeurs des différent champs de type {@link Boolean}
	 */
	private static Map<String, Boolean> booleanMap;
	/**
	 * {@link Map} contennant les valeurs des différent champs de type {@link Character}
	 */
	private static Map<String, Character> charMap;

	/**
	 * methode appellée par une classe externe au package permettant d'appeller tous les utilitaires nécessaire au formulaire
	 * @param filePath  Le chemin du fichier XML permettant de générer le formlaire
	 */
	public static void showForm(String filePath)
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
			scan.close();

			String finalFile = "<?xml version=\"1.0\" ?>\n";
			finalFile += "<!DOCTYPE form SYSTEM \"" + dtdFile.getAbsolutePath() + "\">\n";
			finalFile += file.substring( file.indexOf("<form") ).replaceAll("[\t]", "");
			pw.write( finalFile );

			pw.close();

			if(validXml(xmlFileWithDTD, true) != null)
			{
				File xmlFileWithDTDUniline = File.createTempFile("tmpFile", ".xml");
				xmlFileWithDTDUniline.deleteOnExit();
				PrintWriter pw2 = new PrintWriter(xmlFileWithDTDUniline, "UTF-8");
				pw2.write( finalFile.replaceAll("[\n]", "") );
				pw2.close();

				frame = Frame.createFrame( (Element) (validXml(xmlFileWithDTDUniline).getFirstChild()) );
				pauseUntilWindowClosed();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// le fichier est accepté, on l'envoi à la suite du traitement
		// parseXml(xmlFile);
	}

	private static Element validXml (File fileXML)
	{
		return validXml(fileXML, false);
	}

	/**
	 * cette méthode permet de savoir si le document XML respecte la dtd et écrit des erreurs dans le terminal sinon
	 * @param fileXML le fichier XML a vérifié
	 * @return boolean L'élément si le fichier valide, sinon null
	 */
	private static Element validXml (File fileXML, boolean validate)
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
			if (validate)
			{
				builder.setErrorHandler(errHandler);
			}
			else
			{
				builder.setErrorHandler(null);
			}

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

	private static void pauseUntilWindowClosed()
	{
		frame.setVisible(true);

		windowIsOpen = true;
		while(windowIsOpen)
		{
			try {
				Thread.sleep(100);
			}
			catch (Exception e) {

			}
		}
	}

	public static void windowClosed()
	{
		intMap = new HashMap<String, Integer>();
		doubleMap = new HashMap<String, Double>();
		stringMap = new HashMap<String, String>();
		charMap = new HashMap<String, Character>();
		booleanMap = new HashMap<String, Boolean>();
		for (Control ctrl : frame.getControls())
		{
			// System.out.println(ctrl.getType() + " : " + ctrl.getValues() + " <--> " + ctrl.getId());
			if (!(ctrl instanceof iut.algo.form.view.Array)) {
				switch (ctrl.getType())
				{
					case Int:
						intMap.put(ctrl.getId(), (Integer)(ctrl.getValues()));
					break;

					case Double:
						doubleMap.put(ctrl.getId(), (Double)(ctrl.getValues()));
					break;

					case String:
						stringMap.put(ctrl.getId(), (String)(ctrl.getValues()));
					break;

					case Char:
						if (((String)(ctrl.getValues())).length() > 0)
						{
							charMap.put(ctrl.getId(), ((String)(ctrl.getValues())).charAt(0));
						}
					break;

					case Boolean:
						booleanMap.put(ctrl.getId(), (Boolean)(ctrl.getValues()));
					break;
				}
			}
			else
			{
				// System.out.println("c'est un array");
			}
		}
		frame = null;
		windowIsOpen = false;
	}

	/**
	 * retourne la valeur d'un controle numerique entier
	 * @param  id id du controle
	 * @return la valeur correspondante au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static Integer getInt(String id)
	{
		return intMap.get(id);
	}

	/**
	 * retourne la valeur d'un controle numerique
	 * @param  id id du controle
	 * @return la valeur correspondante au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static Double getDouble(String id)
	{
		return doubleMap.get(id);
	}

	/**
	 * retourne la valeur d'un controle de texte
	 * @param  id id du controle
	 * @return la valeur correspondante au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static String getString(String id)
	{
		return stringMap.get(id);
	}

	/**
	 * retourne la valeur d'un controle de caractere
	 * @param  id id du controle
	 * @return la valeur correspondante au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static Character getChar(String id)
	{
		return charMap.get(id);
	}

	/**
	 * retourne la valeur d'un controle boolean
	 * @param  id id du controle
	 * @return la valeur correspondante au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static Boolean getBoolean(String id)
	{
		return booleanMap.get(id);
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

			pw.write("\t\t\t\t<!ELEMENT fenetre (texte|menu|case|tableau|boutons|calendrier)+>\n");
			pw.write("\t\t\t\t\t<!ATTLIST fenetre longueur CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t                  largeur  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t                  titre    CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t                  x        CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t                  y        CDATA #REQUIRED>\n");
			pw.write("\t\t\t\t\t<!ELEMENT texte EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST texte type  ( chaine | entier | double | booleen | caractere )  #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                y     CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT menu (choix+)>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST menu id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t               label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t               x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t               y     CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT choix EMPTY>\n");
			pw.write("\t\t\t\t\t\t\t<!ATTLIST choix label CDATA #REQUIRED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT case EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST case label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t               id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t               x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t               y     CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT tableau EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST tableau label  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  id     ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  type   ( chaine | entier | double | booleen | caractere ) #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  nb_lig CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  nb_col CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  x      CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                  y      CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT boutons (bouton+)>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST boutons label  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  id     CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  x      CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                  y      CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT bouton (#PCDATA)>\n");
			pw.write("\t\t\t\t\t<!ELEMENT calendrier EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST calendrier label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                     id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                     x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                     y     CDATA #IMPLIED >\n");

			pw.write("\t\t\t\t<!ELEMENT window (text|dropdown|checkbox|array|buttons|calendar)+>\n");
			pw.write("\t\t\t\t\t<!ATTLIST window length CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t                 width  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t                 title  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t                 x      CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t                 y      CDATA #REQUIRED>\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT text (#PCDATA)>\n");
			pw.write("\t\t\t\t\t\t\t<!ATTLIST text type  ( string | int | double | boolean | char ) #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t               label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t               id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t\t               x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t\t               y     CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT dropdown (choice+)>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST dropdown id    ID\t  #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                   label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                   x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                   y     CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT choice EMPTY>\n");
			pw.write("\t\t\t\t\t\t\t<!ATTLIST choice label CDATA #REQUIRED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT checkbox EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST checkbox label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                   id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                   x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                   y     CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT array EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST array label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                type ( string | int | double | boolean | char ) #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                nb_lig CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                nb_col CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                x      CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                y      CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t<!ELEMENT buttons (button+)>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST buttons label  CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  id     CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                  x      CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                  y      CDATA #IMPLIED >\n");
			pw.write("\t\t\t\t\t\t<!ELEMENT button (#PCDATA)>\n");
			pw.write("\t\t\t\t\t<!ELEMENT calendar EMPTY>\n");
			pw.write("\t\t\t\t\t\t<!ATTLIST calendar label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                   id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t\t\t                   x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t\t\t                   y     CDATA #IMPLIED >\n");



			pw.close();

			return dtd;
		}
		catch (Exception e) {}
		return null;
	}
}
