import iut.algo.form.FormController;

/**
 * classe qui permet d'afficher un formulaire de test
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Demo
{
	public static void main (String[] args)
	{
		/* CREATION ET AFFICHAGE DU FORMULAIRE */

		FormController.createForm("../exemple.xml");
		FormController.setValue("a05", 10);
		FormController.showForm();


		int i = FormController.getInt("a05");
		System.out.println(i);
	}
} // - Lang en adéquation avec nom du fils de root. (window / fenetre)
