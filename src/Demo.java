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
		FormController.setValue("a05", "test");
		FormController.showForm();

		if( FormController.isValid() )
		{
			String s = FormController.getString("a05");
			System.out.println(s);

			/*String[][] tab = String[7][7];
			FormController.getArray("a08", tab);
			for (String[] val : tab)
				for (String subval : val)
					System.out.println( subVal );*/
		}
		else
		{
			System.out.println("Erreur, vous avez quitter sans valider");
		}
	}
}
