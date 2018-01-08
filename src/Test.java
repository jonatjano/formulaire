import iut.algo.form.FormController;

/**
 * classe qui permet d'afficher un formulaire de test
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Test
{
	public static void main(String[] args)
	{
		FormController.showForm("monFichier.xml", Test.class);
	}
}
