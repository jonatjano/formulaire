package iut.algo.form;

import java.io.File;
import javax.xml.*;

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
	 * @param classType La classe demandant le formulaire utilisée quand le filePath est un chemin relatif à la classe appellante
	 */
	public static void showForm(String filePath, Class classType)
	{
		String finalPath;
		if (classType != null)
		{
			finalPath = classType.getProtectionDomain().getCodeSource().getLocation().getPath() + "/" + filePath;
		}
		else
		{
			finalPath = filePath;
		}
		final File xmlFile = new File(finalPath);
		System.out.println(xmlFile.getAbsolutePath());
		if (!xmlFile.exists())
		{
			System.out.println("Le fichier entré n'existe pas");
		}
		else if (!xmlFile.getName().toUpperCase().endsWith(".XML"))
		{
			System.out.println("Le fichier entré ne correspond pas à un fichier XML");
		}
	}
}
