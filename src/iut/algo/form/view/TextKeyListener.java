package iut.algo.form.view;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * KeyListener utilisé pour revenir en arrière lorsque un Text est sélectionné
 * @author Team Infotik
 * @version 2018-01-10
 */
public class TextKeyListener implements KeyListener
{
	/** Control auquel le listener est lié */
	private Text control;

	/**
	 * Crée le listener de l'élément possédant la possibilité d'un Ctrl+Z
	 * @param frame La frame
	 * @param control Controle à lier à au listener
	 */
	public TextKeyListener (Text control)
	{
		this.control = control;
	}

	/**
	 * Pas utilisé
	 * @param event L'événement
	 */
	public void keyReleased (KeyEvent event) {}

	/**
	 * Méthode appelée quand l'utilisateur appuie sur Ctrl+Z
	 * @param event L'évenement appellant la méthode
	 */
	public void keyPressed (KeyEvent event)
	{
		if ( event.getSource() instanceof JTextField )
		{
			if ( event.getKeyCode() == KeyEvent.VK_Z &&
				 KeyEvent.getKeyModifiersText(event.getModifiers()).equals("Ctrl") )
			{
				this.control.revert();
			}
		}
	}

	/**
	 * Pas utilisé
	 * @param event L'événement
	 */
	public void keyTyped (KeyEvent event) {}
}
