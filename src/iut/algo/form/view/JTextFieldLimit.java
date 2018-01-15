package iut.algo.form.view;

import javax.swing.JTextField;

import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;

/**
 * Champ d'insertion de texte avec une limite de caractères
 * @author Team Infotik
 * @version 2018-01-10
 */
public class JTextFieldLimit extends JTextField
{
	/**
	 * le nombre limite de caractère
	 */
	private int limit;


	/**
	 * JTextField avec une limite de caractères pouvant y être rentrées
	 * @param limit limite de caractères
	 */
	public JTextFieldLimit (int limit)
	{
		super();
		this.limit = limit;
	}

	/**
	 * Modifie le modèle de la zone de texte, pour permettre de limiter
	 * l'entrée de l'utilisateur
	 * @param Modèle de l'élément
	 * @return Le modèle utilisé par défault
	 */
	@Override
	protected Document createDefaultModel ()
	{
		return new LimitDocument();
	}

	/**
	 * Modèle permettant de limiter le nombre de caractères rentrés
	 * par l'utilisateur
	 * @author Team Infotik
	 * @version 2018-01-10
	 */
	private class LimitDocument extends PlainDocument
	{
		@Override
		public void insertString (int offset, String  str, AttributeSet attr)
		throws BadLocationException
		{
			// S'il n'y a aucun contenu, aucune action n'est effectuée
			if (str == null) return;

			// Sinon, vérifie si la chaine rentrée dépasse le nombre de caractères limite
			// Si c'est le cas, la chaine n'est pas insérée dans la zone de texte
			if ( (this.getLength() + str.length()) <= limit )
				super.insertString(offset, str, attr);
		}
	}
}
