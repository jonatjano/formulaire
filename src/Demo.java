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
		FormController.setValue("05", "test");
		FormController.showForm();

		if( FormController.isValid() )
		{
			String s = FormController.getString("05");
			System.out.println(s);

			String[][] tab = new String[7][8];
			FormController.getArrayString("a08", tab);
			for (String[] truc : tab)
				for (String str : truc)
					System.out.println( (str == null) ? " / " : str.toString());
		}
		else
		{
			System.out.println("Erreur, vous avez quitter sans valider");
		}
	}
}
