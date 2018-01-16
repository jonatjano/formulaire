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

		FormController fm = FormController.createAndGetForm("exemple.xml");
		fm.showForm();
		fm.setValue("06", new String[] {"test1","test2","test3"});
		fm.showForm();
		fm.close();

		if( fm.isValid() )
		{
			String s = fm.getString("05");
			System.out.println(s);

			boolean[][] tab = new boolean[2][4];
			System.out.print("As-t-on bien récupéré l'array ? ");
			System.out.println( fm.getArrayBoolean("11", tab) );
			for (boolean[] boolLigne : tab)
			{
				for (boolean bool : boolLigne)
				{
					System.out.print( bool + ", " );
				}
			}
		}
		else
		{
			System.out.println("ERREUR : Vous avez quitté sans valider.");
		}
	}
}
