import iut.algo.form.FormController;
import iut.algo.form.view.Frame;

/**
 * classe qui permet d'afficher un formulaire de test
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Demo
{
	public static void main (String[] args)
	{
		FormController.createForm("../exemple.xml");
		FormController.setValue("a05", 10);
		FormController.showForm();
		int i = FormController.getInt("a05");
		System.out.println(i);
	}
}
