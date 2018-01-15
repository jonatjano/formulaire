package iut.algo.form;

import iut.algo.form.job.SimpleErrorHandler;
import iut.algo.form.view.Control;
import iut.algo.form.view.Frame;
import iut.algo.form.view.Array;
import iut.algo.form.job.BaseType;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
	private static File 					dtdFile = createDtdFile();
	/**
	 * Fait savoir au programme si un formulaire est actuellement ouvert
	 */
	private static boolean 					windowIsOpen = false;
	/**
	 * La fenêtre du dernier formulaire
	 */
	private static Frame 					frame;
	/**
	 * {@link Map} contenant les valeurs des différents champs de type {@link Integer}
	 */
	private static Map<String, Integer> 	intMap;
	/**
	 * {@link Map} contenant les valeurs des différents champs de type {@link String}
	 */
	private static Map<String, String> 		stringMap;
	/**
	 * {@link Map} contenant les valeurs des différents champs de type {@link Double}
	 */
	private static Map<String, Double> 		doubleMap;
	/**
	 * {@link Map} contenant les valeurs des différents champs de type {@link Boolean}
	 */
	private static Map<String, Boolean> 	booleanMap;
	/**
	 * {@link Map} contenant les valeurs des différents champs de type {@link Character}
	 */
	private static Map<String, Character> 	charMap;
	/**
	 * {@link Map} contenant les valeurs des différents champs de type tableaux
	 */
	private static Map<String, Object[][]> 	arrayMap;

	private static List<String[]> listTypeErr;
	
	private static List<Integer> listOrdinalBut;
	
	private static List<Integer> listId;

	/**
	 * methode appellée par une classe externe au package permettant d'appeller tous les utilitaires nécessaire au formulaire
	 * cette méthode n'affiche pas le formulaire, il faut pour cela appeler showForm
	 * @param filePath  Le chemin du fichier XML permettant de générer le formlaire
	 */
	public static void createForm (String filePath)
	{
		listTypeErr = new ArrayList<String[]>();
		listOrdinalBut = new ArrayList<Integer>();
		listId = new ArrayList<Integer>();
		// on recupere le fichier
		File xmlFile = new File(filePath);

		// on fait les verifications basique d'existence du fichier
		// S'il existe...
		if (!xmlFile.exists())
		{
			showError("Le fichier ciblé n'existe pas");
			return;
		}
		// Et s'il possède bien une extension XML...
		else if ( !xmlFile.getName().toUpperCase().endsWith(".XML") )
		{
			showError("Le fichier ciblé ne correspond pas à un fichier XML");
			return;
		}


		// ... Le programme continue !
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

			String finalFile = "<?xml version=\"1.0\" ?>";
			finalFile += "<!DOCTYPE form SYSTEM \"" + dtdFile.getAbsolutePath() + "\">";
			finalFile += file.substring( 0, file.indexOf("<form") ).replaceAll("[^\n]", "").replaceFirst("[\n]", "");
			finalFile += file.substring( file.indexOf("<form") );
			pw.write( finalFile );

			pw.close();

			Element frameRoot = validXml(xmlFileWithDTD);
			if (frameRoot != null)
			{
				frame = Frame.createFrame( (Element) (frameRoot.getFirstChild()) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// le fichier est accepté, on l'envoie à la suite du traitement
		// parseXml(xmlFile);
	}

	/**
	 * methode utilisée pour afficher le dernier formulaire créé
	 */
	public static void showForm()
	{
		if (frame != null)
		{
			pauseUntilWindowClosed();
		}
		else
		{
			showError("Il n'existe aucun formulaire pour le moment");
		}
	}

	/**
	 * Vérifie le fichier XML passé en paramètre, en écrivant les éventuels erreurs sur un un popup d'erreur visible par
	 * l'utilisateur
	 * @param fileXML le fichier XML a vérifié
	 * @param validate Valeur indiquant si le fichier doit être valide pour continuer
	 * @return L'élément si le fichier valide, sinon null
	 */
	private static Element validXml (File fileXML)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);

		try
		{
			//Méthode qui permet d'activer la vérification du fichier
			factory.setValidating(true);

			DocumentBuilder builder = factory.newDocumentBuilder();

			// Création de l'objet gérant les erreurs
			ErrorHandler errHandler = new SimpleErrorHandler();
			//Affectation de notre objet au document pour interception des erreurs éventuelles
			builder.setErrorHandler(errHandler);

			//On rajoute un bloc de capture
			//pour intercepter les erreurs au cas où il y en a

			try
			{
				Document xml = builder.parse(fileXML);
				Element root = xml.getDocumentElement();
				if (verifType(root))
				{
					return root;
				}
				else
				{
					String[] err = listTypeErr.get(0);
					
					showError(err);
				}
			}
			catch (SAXParseException e)
			{}
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
			System.out.println("Parser");
		}
		catch (SAXException e)
		{
			e.printStackTrace();
			System.out.println("Sax");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("IO");
		}
		return null;
	}

	private static boolean attributeOk(Element elem, String attName, String attType)
	{
		if (!elem.hasAttribute(attName))
			return true;

		String value = elem.getAttribute(attName);

		switch (attType)
		{
			case "int":
				try
				{
					Integer.parseInt(value);
				}
				catch(Exception e)
				{
					listTypeErr.add(new String[]{ "TYPE_ERR",
												  attName,
												  elem.getTagName(),
												  "entier",
												  value
												}
									);

					return false;
				}
				break;
			
			case "id":
				String valueTest = value.replaceAll("[^0-9]","");
				if (valueTest.equals(""))
				{
					listTypeErr.add(new String[]{ "ID_NO_DIGIT",
												  elem.getTagName(),
												  value
												}
									);
					return false;
				}
				try
				{
					Integer i = Integer.parseInt(valueTest);
					if ( listId.contains(i) )
					{
						listTypeErr.add(new String[]{ "ID_DUPLICATED",
												  elem.getTagName(),
												  i.toString()
												}
									);
						return false;
					}
					else
					{
						listId.add(i);
						elem.setAttribute("id",i.toString());
					}
				}
				catch(Exception e)
				{
					return false; // pas atteignable
				}
				break;
				
			case "ordinal":
				try
				{
					int num = Integer.parseInt(value);
					if (listOrdinalBut.contains(num))
					{
						Element parent = (Element)elem.getParentNode();
						listTypeErr.add(new String[] { "CORD_DOUBLE_ERR",
													   parent.getAttribute("id"),
													   num + ""
													 }
									   );
						return false;
					}
					
					listOrdinalBut.add(num);
				}
				catch (Exception ex)
				{
					return false; // Pas accessible normalement
				}
				break;
		}

		return true;
	}

	private static boolean attributeOk(Element elem)
	{
		return attributeOk(elem, "longeur", "int") &
			   attributeOk(elem, "largeur", "int") &
			   attributeOk(elem, "x", "int") &
			   attributeOk(elem, "y", "int") &
			   attributeOk(elem, "nb_lig", "int") &
			   attributeOk(elem, "nb_row", "int") &
			   attributeOk(elem, "nb_col", "int") &
			   attributeOk(elem, "length", "int") &
			   attributeOk(elem, "width", "int") &
			   attributeOk(elem, "id", "id") &
			   attributeOk(elem, "ordinal", "int") &&
			   attributeOk(elem, "ordinal", "ordinal");
	}

	private static boolean verifType(Element root)
	{
		boolean ok = true;
		NodeList nodeList = root.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if (node instanceof Element)
			{
				Element elem = (Element)node;

				if (elem.getTagName().matches(".*((boutons)|(buttons)).*"))
					listOrdinalBut.clear();
				
				if ( verifType(elem) == false)
					ok = false;

				if (!attributeOk(elem))
					ok = false;
			}
		}

		return ok;
	}
	
	public static void showError (String[] err)
	{
		String sErr = "";
		switch (err[0])
		{
			case "TYPE_ERR":
				sErr = "L'attribut \"" + err[1] + "\" de l'élement \"" + err[2] + "\" n'est pas un " + err[3] + " !  ( valeur : \"" + err[4] + "\")";

				if (listTypeErr.size() > 1)
					sErr += "\n\t\t( " + ( listTypeErr.size() - 1 ) + " autre" + ( listTypeErr.size() > 2 ? "s" : "" ) + " )";
				
				break;
			
			case "CORD_DOUBLE_ERR":
				sErr = "doublon sur l'ordinal \"" + err[2] + "\" de la liste des bouton radio de l'element avec id=\"" + err[1] + "\" !";
				break;
			case "ID_NO_DIGIT":
				sErr = "l'élément \"" + err[1] + "\" n'a pas de chiffre dans son id ! ( valeur : \"" + err[2] + "\")" ;
				break;
			case "ID_DUPLICATED":
				sErr = "l'id \"" + err[2] + "\" de l'élément \"" + err[1] + "\" à déjà été rencontrer !" ;
				break;
		}
		showError(sErr);
	}

	/**
	 * Cette méthode permet d'afficher une erreur à l'écran avec le titre "Erreur"
	 * @param message Message à afficher dans l'erreur
	 */
	public static void showError (String message)
	{
		showMessage("Erreur", message);
	}

	/**
	 * Cette méthode permet d'afficher une erreur à l'écran
	 * @param titre Titre du message d'erreur
	 * @param message Message à afficher dans l'erreur
	 */
	public static void showMessage( String titre , String message )
	{
		JOptionPane.showMessageDialog( null, message, titre, JOptionPane.ERROR_MESSAGE );
	}

	/**
	 * Empêche le programme de progresser tant qu'un formulaire est lancé
	 */
	private static void pauseUntilWindowClosed ()
	{
		FormController.frame.setVisible(true);

		FormController.windowIsOpen = true;
		while ( FormController.windowIsOpen )
		{
			try					{ Thread.sleep(100); }
			catch (Exception e) { }
		}
	}

	/**
	 * cache la fenetre pour pouvoir la réutiliser
	 */
	public static void windowClosed ()
	{
		frame.setVisible(false);
		windowIsOpen = false;
	}

	/**
	 * Enregistre l'intégralité des informations rentrées par l'utilisateur lors de la
	 * fermeture de la fenêtre
	 */
	public static void windowValidated ()
	{
		intMap		= new HashMap<String, Integer>();
		doubleMap	= new HashMap<String, Double>();
		stringMap	= new HashMap<String, String>();
		charMap		= new HashMap<String, Character>();
		booleanMap	= new HashMap<String, Boolean>();
		arrayMap	= new HashMap<String, Object[][]>();

		for (Control ctrl : frame.getControls())
		{
			// System.out.println(ctrl.getType() + " : " + ctrl.getValue() + " <--> " + ctrl.getId());
			if ( !(ctrl instanceof Array) )
			{
				switch (ctrl.getType())
				{
					case Int:
						intMap.put(ctrl.getId(), (Integer) ctrl.getValue());
					break;

					case Double:
						doubleMap.put(ctrl.getId(), (Double) ctrl.getValue());
					break;

					case String:
						stringMap.put(ctrl.getId(), (String) ctrl.getValue());
					break;

					case Char:
						if (((String)(ctrl.getValue())).length() > 0)
						{
							charMap.put(ctrl.getId(), ((String) ctrl.getValue()).charAt(0));
						}
					break;

					case Boolean:
						booleanMap.put(ctrl.getId(), (Boolean) (ctrl.getValue()));
					break;
				}
			}
			else
			{
				arrayMap.put( ctrl.getId(), ((Array) ctrl).getValue() );
			}
		}
		frame			= null;
		windowIsOpen	= false;
	}

	/**
	 * permet de savoir si le dernier formulaire à été validé
	 * @return true si le dernier formulaire à été validé
	 */
	public static boolean isValid()
	{
		return frame == null;
	}

	/**
	 * Renvoie la valeur d'un controle numerique entier
	 * @param  id Identifiant du controle
	 * @return La valeur correspondant au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static Integer getInt (String id)
	{
		return intMap.get(id);
	}

	/**
	 * Renvoie la valeur d'un controle numerique
	 * @param  id Identifiant du controle
	 * @return La valeur correspondant au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static Double getDouble (String id)
	{
		return doubleMap.get(id);
	}

	/**
	 * Renvoie la valeur d'un controle de texte
	 * @param  id Identifiant du controle
	 * @return La valeur correspondant au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static String getString (String id)
	{
		return stringMap.get(id);
	}

	/**
	 * Renvoie la valeur d'un controle de caractere
	 * @param  id Identifiant du controle
	 * @return La valeur correspondant au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static Character getChar (String id)
	{
		return charMap.get(id);
	}

	/**
	 * Renvoie la valeur d'un controle boolean
	 * @param  id Identifiant du controle
	 * @return La valeur correspondant au controle ou null si l'id est incorrecte ou ne correspond pas à ce type
	 */
	public static Boolean getBoolean (String id)
	{
		return booleanMap.get(id);
	}


	/**
	 * Renvoie la valeur d'un controle Array
	 * @param id Identifiant du controle
	 * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	 * @return Vrai si la copie est un succès, sinon faux
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean getArray (String id, T[][] res)
	{
		try
		{
			T[][] tmp = (T[][]) arrayMap.get(id);

			for (int i = 0; i < res.length && i < tmp.length; i++)
				for (int j = 0; j < res[i].length && j < tmp[i].length; j++)
				{
					// BaseType type = ctrl.getType();
					// if (type == BaseType.Int && tmp[i][j] == null)		res[i][j] = new Integer(0);
					// else												res[i][j] = tmp[i][j];
					res[i][j] = tmp[i][j];
				}

			return true;
		}
		catch (Exception e) { e.printStackTrace(); }

		return false;
	}

	/**
	 * Renvoie la valeur d'un controle Array
	 * @param id Identifiant du controle
	 * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	 * @return Vrai si la copie est un succès, sinon faux
	 */
	@SuppressWarnings("unchecked")
	public static boolean getArrayString (String id, String[][] res)
	{
		try
		{
			String[][] tmp = (String[][]) arrayMap.get(id);

			for (int i = 0; i < res.length && i < tmp.length; i++)
				for (int j = 0; j < res[i].length && j < tmp[i].length; j++)
				{
					if (tmp[i][j] == null)	res[i][j] = "";
					else					res[i][j] = tmp[i][j];
				}

			return true;
		}
		catch (Exception e) { e.printStackTrace(); }

		return false;
	}

	/**
	 * Renvoie la valeur d'un controle Array
	 * @param id Identifiant du controle
	 * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	 * @return Vrai si la copie est un succès, sinon faux
	 */
	@SuppressWarnings("unchecked")
	public static boolean getArrayString (String id, String[] res)
	{
		try
		{
			// String[][] values = (String[][]) arrayMap.get(id);
			// if ( values.length == 0 )
			// {
			String[][] tmp = (String[][]) arrayMap.get(id);

			for (int j = 0; j < res.length && j < tmp[0].length; j++)
			{
				if (tmp[0][j] == null)	res[j] = "";
				else					res[j] = tmp[0][j];
			}
			// }

			return true;
		}
		catch (Exception e) { e.printStackTrace(); }

		return false;
	}

	/**
	 * Renvoie la valeur d'un controle Array
	 * @param id Identifiant du controle
	 * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	 * @return Vrai si la copie est un succès, sinon faux
	 */
	@SuppressWarnings("unchecked")
	public static boolean getArrayInt (String id, int[][] res)
	{
		try
		{
			Integer[][] tmp = (Integer[][]) arrayMap.get(id);

			for (int i = 0; i < res.length && i < tmp.length; i++)
				for (int j = 0; j < res[i].length && j < tmp[i].length; j++)
				{
					if (tmp[i][j] == null)	res[i][j] = 0;
					else					res[i][j] = tmp[i][j];
				}

			return true;
		}
		catch (Exception e) { e.printStackTrace(); }

		return false;
	}

	/**
	 * Renvoie la valeur d'un controle Array
	 * @param id Identifiant du controle
	 * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	 * @return Vrai si la copie est un succès, sinon faux
	 */
	@SuppressWarnings("unchecked")
	public static boolean getArrayInt (String id, int[] res)
	{
		try
		{
			Integer[][] tmp = (Integer[][]) arrayMap.get(id);

			for (int j = 0; j < res.length && j < tmp[0].length; j++)
			{
				if (tmp[0][j] == null)	res[j] = 0;
				else					res[j] = tmp[0][j];
			}

			return true;
		}
		catch (Exception e) { e.printStackTrace(); }

		return false;
	}

	/**
	 * Renvoie la valeur d'un controle Array
	 * @param id Identifiant du controle
	 * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	 * @return Vrai si la copie est un succès, sinon faux
	 */
	@SuppressWarnings("unchecked")
	public static boolean getArrayDouble (String id, double[][] res)
	{
		try
		{
			Double[][] tmp = (Double[][]) arrayMap.get(id);

			for (int i = 0; i < res.length && i < tmp.length; i++)
				for (int j = 0; j < res[i].length && j < tmp[i].length; j++)
				{
					if (tmp[i][j] == null)	res[i][j] = 0.0;
					else					res[i][j] = tmp[i][j];
				}

			return true;
		}
		catch (Exception e) { e.printStackTrace(); }

		return false;
	}

	/**
	 * Renvoie la valeur d'un controle Array
	 * @param id Identifiant du controle
	 * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	 * @return Vrai si la copie est un succès, sinon faux
	 */
	@SuppressWarnings("unchecked")
	public static boolean getArrayChar (String id, char[][] res)
	{
		try
		{
			Character[][] tmp = (Character[][]) arrayMap.get(id);

			for (int i = 0; i < res.length && i < tmp.length; i++)
				for (int j = 0; j < res[i].length && j < tmp[i].length; j++)
				{
					if (tmp[i][j] == null)	res[i][j] = Character.MIN_VALUE;
					else					res[i][j] = tmp[i][j];
				}

			return true;
		}
		catch (Exception e) { e.printStackTrace(); }

		return false;
	}

	/**
	 * Renvoie la valeur d'un controle Array
	 * @param id Identifiant du controle
	 * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	 * @return Vrai si la copie est un succès, sinon faux
	 */
	@SuppressWarnings("unchecked")
	public static boolean getArrayBoolean (String id, boolean[][] res)
	{
		try
		{
			Boolean[][] tmp = (Boolean[][]) arrayMap.get(id);

			for (int i = 0; i < res.length && i < tmp.length; i++)
				for (int j = 0; j < res[i].length && j < tmp[i].length; j++)
				{
					if (tmp[i][j] == null)	res[i][j] = false;
					else					res[i][j] = tmp[i][j];
				}

			return true;
		}
		catch (Exception e) { e.printStackTrace(); }

		return false;
	}


	// /**
	//  * Renvoie la valeur d'un controle Array
	//  * @param id Identifiant du controle
	//  * @param res Tableau de l'utilisateur à remplir d'une copie du tableau auquel il tente d'accéder
	//  * @return Vrai si la copie est un succès, sinon faux
	//  */
	// public static boolean getArrayString (String id, String[] res)
	// {
	// 	try
	// 	{
	// 		String[] tmp = (String[]) arrayMap.get(id);

	// 		for (int i = 0; i < res.length && i < tmp.length; i++)
	// 			for (int j = 0; j < res[i].length && i < tmp[i].length; j++)
	// 				res[i][j] = tmp[i][j];

	// 		return true;
	// 	}
	// 	catch (Exception e) { e.printStackTrace(); }

	// 	return false;
	// }

	/**
	 * méthode utilisée pour changer les valeurs des différents composants
	 * doit être utilisée entre createForm et showForm
	 * un message est affiché en cas d'erreur et le changement n'est pas fait
	 * @param id    l'id du controle à modifier
	 * @param value la valeur à donner au controle
	 * @return vrai si le changement à réussi sinon faux
	 */
	public static boolean setValue(String id, Object value)
	{
		if (frame != null)
		{
			for (Control control : frame.getControls())
			{
				if (control.getId().equals(id))
				{
					return control.setValue(value);
				}
			}
		}
		return false;
	}

	/**
	 * permet d'obtenir un fichier contenant la dtd
	 * c'est un fichier temporaire supprimé à la fin de l'execution
	 * @return le fichier contenant la dtd
	 */
	private static File createDtdFile()
	{
		try
		{
			File dtd = File.createTempFile("dtd", ".dtd");
			dtd.deleteOnExit();
			return createDtdFile(dtd);
		}
		catch(Exception e) {}
		return null;
	}

	/**
	 * Crée la DTD dans un fichier temporaire du système
	 * @param dtd fichier dans lequel est écrit la dtd
	 * @return File Le fichier DTD créé
	 */
	private static File createDtdFile(File dtd)
	{
		try {
			PrintWriter pw = new PrintWriter(dtd, "UTF-8");

			pw.write("<!ELEMENT form (fenetre|window)>\n");

			pw.write("\t<!ELEMENT fenetre (label|texte|menu|case|tableau|boutons|calendrier)+>\n");
			pw.write("\t\t<!ATTLIST fenetre longueur CDATA #REQUIRED\n");
			pw.write("\t\t                  largeur  CDATA #REQUIRED\n");
			pw.write("\t\t                  titre    CDATA #REQUIRED\n");
			pw.write("\t\t                  x        CDATA #REQUIRED\n");
			pw.write("\t\t                  y        CDATA #REQUIRED>\n");
			pw.write("\t\t<!ELEMENT texte EMPTY>\n");
			pw.write("\t\t\t<!ATTLIST texte type  ( chaine | entier | double | caractere )  #REQUIRED\n");
			pw.write("\t\t\t                label CDATA #REQUIRED\n");
			pw.write("\t\t\t                id    ID    #REQUIRED\n");
			pw.write("\t\t\t                x     CDATA #IMPLIED\n");
			pw.write("\t\t\t                y     CDATA #IMPLIED >\n");
			pw.write("\t\t<!ELEMENT menu (choix*)>\n");
			pw.write("\t\t\t<!ATTLIST menu id    ID    #REQUIRED\n");
			pw.write("\t\t\t               label CDATA #REQUIRED\n");
			pw.write("\t\t\t               x     CDATA #IMPLIED\n");
			pw.write("\t\t\t               y     CDATA #IMPLIED >\n");
			pw.write("\t\t\t<!ELEMENT choix EMPTY>\n");
			pw.write("\t\t\t\t<!ATTLIST choix label    CDATA #REQUIRED >\n");
			pw.write("\t\t<!ELEMENT case EMPTY>\n");
			pw.write("\t\t\t<!ATTLIST case label CDATA #REQUIRED\n");
			pw.write("\t\t\t               id    ID    #REQUIRED\n");
			pw.write("\t\t\t               x     CDATA #IMPLIED\n");
			pw.write("\t\t\t               y     CDATA #IMPLIED >\n");
			pw.write("\t\t<!ELEMENT tableau EMPTY>\n");
			pw.write("\t\t\t<!ATTLIST tableau label  CDATA #REQUIRED\n");
			pw.write("\t\t\t                  id     ID    #REQUIRED\n");
			pw.write("\t\t\t                  type   ( chaine | entier | double | booleen | caractere ) #REQUIRED\n");
			pw.write("\t\t\t                  nb_lig CDATA #REQUIRED\n");
			pw.write("\t\t\t                  nb_col CDATA #REQUIRED\n");
			pw.write("\t\t\t                  x      CDATA #IMPLIED\n");
			pw.write("\t\t\t                  y      CDATA #IMPLIED >\n");
			pw.write("\t\t<!ELEMENT boutons (bouton+)>\n");
			pw.write("\t\t\t<!ATTLIST boutons label  CDATA #REQUIRED\n");
			pw.write("\t\t\t                  id     CDATA #REQUIRED\n");
			pw.write("\t\t\t                  x      CDATA #IMPLIED\n");
			pw.write("\t\t\t                  y      CDATA #IMPLIED >\n");
			pw.write("\t\t\t<!ELEMENT bouton (#PCDATA)>\n");
			pw.write("\t\t\t\t<!ATTLIST bouton ordinal    CDATA #REQUIRED >\n");
			pw.write("\t\t<!ELEMENT calendrier EMPTY>\n");
			pw.write("\t\t\t<!ATTLIST calendrier label CDATA #REQUIRED\n");
			pw.write("\t\t\t                     id    ID    #REQUIRED\n");
			pw.write("\t\t\t                     x     CDATA #IMPLIED\n");
			pw.write("\t\t\t                     y     CDATA #IMPLIED >\n");

			pw.write("\t<!ELEMENT window (label|text|dropdown|checkbox|array|buttons|calendar)+>\n");
			pw.write("\t\t<!ATTLIST window length CDATA #REQUIRED\n");
			pw.write("\t\t                 width  CDATA #REQUIRED\n");
			pw.write("\t\t                 title  CDATA #REQUIRED\n");
			pw.write("\t\t                 x      CDATA #REQUIRED\n");
			pw.write("\t\t                 y      CDATA #REQUIRED>\n");
			pw.write("\t\t\t<!ELEMENT text (#PCDATA)>\n");
			pw.write("\t\t\t\t<!ATTLIST text type  ( string | int | double | char ) #REQUIRED\n");
			pw.write("\t\t\t\t               label CDATA #REQUIRED\n");
			pw.write("\t\t\t\t               id    ID    #REQUIRED\n");
			pw.write("\t\t\t\t               x     CDATA #IMPLIED\n");
			pw.write("\t\t\t\t               y     CDATA #IMPLIED >\n");
			pw.write("\t\t<!ELEMENT dropdown (choice*)>\n");
			pw.write("\t\t\t<!ATTLIST dropdown id    ID\t  #REQUIRED\n");
			pw.write("\t\t\t                   label CDATA #REQUIRED\n");
			pw.write("\t\t\t                   x     CDATA #IMPLIED\n");
			pw.write("\t\t\t                   y     CDATA #IMPLIED >\n");
			pw.write("\t\t\t<!ELEMENT choice EMPTY>\n");
			pw.write("\t\t\t\t<!ATTLIST choice label    CDATA #REQUIRED >\n");
			pw.write("\t\t<!ELEMENT checkbox EMPTY>\n");
			pw.write("\t\t\t<!ATTLIST checkbox label CDATA #REQUIRED\n");
			pw.write("\t\t\t                   id    ID    #REQUIRED\n");
			pw.write("\t\t\t                   x     CDATA #IMPLIED\n");
			pw.write("\t\t\t                   y     CDATA #IMPLIED >\n");
			pw.write("\t\t<!ELEMENT array EMPTY>\n");
			pw.write("\t\t\t<!ATTLIST array label CDATA #REQUIRED\n");
			pw.write("\t\t\t                id    ID    #REQUIRED\n");
			pw.write("\t\t\t                type ( string | int | double | boolean | char ) #REQUIRED\n");
			pw.write("\t\t\t                nb_row CDATA #REQUIRED\n");
			pw.write("\t\t\t                nb_col CDATA #REQUIRED\n");
			pw.write("\t\t\t                x      CDATA #IMPLIED\n");
			pw.write("\t\t\t                y      CDATA #IMPLIED >\n");
			pw.write("\t\t<!ELEMENT buttons (button+)>\n");
			pw.write("\t\t\t<!ATTLIST buttons label  CDATA #REQUIRED\n");
			pw.write("\t\t\t                  id     CDATA #REQUIRED\n");
			pw.write("\t\t\t                  x      CDATA #IMPLIED\n");
			pw.write("\t\t\t                  y      CDATA #IMPLIED >\n");
			pw.write("\t\t\t<!ELEMENT button (#PCDATA)>\n");
			pw.write("\t\t\t\t<!ATTLIST button ordinal    CDATA #REQUIRED >\n");
			pw.write("\t\t<!ELEMENT calendar EMPTY>\n");
			pw.write("\t\t\t<!ATTLIST calendar label CDATA #REQUIRED\n");
			pw.write("\t\t\t                   id    ID    #REQUIRED\n");
			pw.write("\t\t\t                   x     CDATA #IMPLIED\n");
			pw.write("\t\t\t                   y     CDATA #IMPLIED >\n");

			pw.write("\t\t<!ELEMENT label EMPTY>\n");
			pw.write("\t\t\t<!ATTLIST label id    ID    #REQUIRED\n");
			pw.write("\t\t\t                label CDATA #REQUIRED\n");
			pw.write("\t\t\t                x     CDATA #IMPLIED\n");
			pw.write("\t\t\t                y     CDATA #IMPLIED >\n");



			pw.close();

			return dtd;
		}
		catch (Exception e) {}

		// Renvoie null s'il y a eu une erreur lors de la création
		return null;
	}

	/**
	 * enregistre le fichier dtd sous fileNameNoExt.dtd
	 * @param fileNameNoExt nom sans extension du fichier destination
	 */
	public static void saveDtdAs(String fileNameNoExt)
	{
		File dtdFile = new File(fileNameNoExt + ".dtd");
		createDtdFile(dtdFile);
	}

	/**
	 * méthode utilisée pour obtenir la dtd
	 * @param args doit contenir une valeur étant le nom du fichier dtd à créer sans extension
	 */
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			System.out.println("Ce main permet de créer un fichier dtd où l'on veux");
			System.out.println("Usage : java iut.algo.form.FormController <nom du fichier sans extension>");
		}
		else
		{
			saveDtdAs(args[0]);
		}
	}
}
