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

			int[][] tab = new int[2][4];
			System.out.println( FormController.getArrayInt("10", tab) );
			for (int[] truc : tab)
			{
				for (int machin : truc)
				{
					System.out.print( machin + ", " );
				}
			}
		}
		else
		{
			System.out.println("ERREUR : Vous avez quitté sans valider.");
		}
	}
}
