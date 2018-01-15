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

		FormController fm = FormController.createAndGetForm("../exemple.xml");
		fm.setValue("05", "test");
		fm.showForm();

		if( fm.isValid() )
		{
			String s = fm.getString("05");
			System.out.println(s);

			// int[] tab = new int[4][1];
			// System.out.println( FormController.getArrayInt("a10", tab) );
			// for (int[] truc : tab)
			// {
			// 	for (int str : truc)
			// 	{
			// 		System.out.print( str + ", " );
			// 	}
			// 	System.out.println();
			// }

			int[] tab = new int[4];
			System.out.println( fm.getArrayInt("a10", tab) );
			for (int truc : tab)
			{
				System.out.print( truc + ", " );
			}
		}
		else
		{
			System.out.println("ERREUR : Vous avez quitté sans valider.");
		}
	}
}
