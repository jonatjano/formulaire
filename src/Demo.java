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
		FormController fm2 = FormController.createAndGetForm("../exemple.xml");
		fm2.showForm();
		fm.setValue("6", new String[] {"coucou","ça","marche"});
		fm2.showForm();
		fm2.close();
		System.out.println("a");

		if( fm2.isValid() )
		{
			String s = fm.getString("5");
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

			boolean[][] tab = new boolean[2][4];
			System.out.println( fm.getArrayBoolean("11", tab) );
			for (boolean[] truc : tab)
			{
				for (boolean machin : truc)
				{
					System.out.print( machin + ", " );
				}
			}
			System.out.println("s");
		}
		else
		{
			System.out.println("ERREUR : Vous avez quitté sans valider.");
		}
	}
}
