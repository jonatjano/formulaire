import iut.algo.form.FormController;
import iut.algo.form.view.Frame;

/**
 * classe qui permet d'afficher un formulaire de test
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Test
{
	public static void main(String[] args)
	{
		System.out.println("aa");
		FormController.showForm("../exemple.xml");
		System.out.println("a");
		FormController.showForm("../exemple.xml");
		System.out.println("b");
	}
}
