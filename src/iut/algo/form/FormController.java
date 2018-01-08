package iut.algo.form;

import java.io.File;
import iut.algo.form.job.xml.ReadFile;

/**
 * classe permettant de controller les formulaires depuis des programmes exterieurs
 * @author Team Infotik
 * @version 2018-01-08
 */
public class FormController
{
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
		// on cherche le chemin du fichier
		String finalPath;
		// c'est un chemin relatif à la classe
		if (classType != null)
		{
			// on recupere le chemin menant à la classe auquel on ajoute le chemn relatif paramètre
			finalPath = classType.getProtectionDomain().getCodeSource().getLocation().getPath() + "/" + filePath;
		}
		// c'est un chemin absolu
		else
		{
			// on me juste le chemin
			finalPath = filePath;
		}
		// creation du fichier
		File xmlFile = new File(finalPath);

		// on fait les verifications basique d'existence du fichier
		// s'il exist
		if (!xmlFile.exists())
		{
			System.out.println("Le fichier entré n'existe pas");
			return;
		}
		// s'il possède bien une extension XML
		else if (!xmlFile.getName().toUpperCase().endsWith(".XML"))
		{
			System.out.println("Le fichier entré ne correspond pas à un fichier XML");
			return;
		}

		ReadFile.read(xmlFile);
	}
}
