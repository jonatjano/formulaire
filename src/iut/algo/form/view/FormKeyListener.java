package iut.algo.form.view;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * KeyListener utilisé pour afficher et cacher les identifiants et types des éléments du
 * formulaire
 * @author Team Infotik
 * @version 2018-01-10
 */
public class FormKeyListener implements KeyListener
{
	/** Objet "Frame" du programme */
	private Frame frame;

	/**
	 * Crée le listener de la fenêtre
	 * @param frame La frame
	 */
	public FormKeyListener (Frame frame)
	{
		this.frame = frame;
	}

	/**
	 * Pas utilisé
	 * @param event L'événement
	 */
	public void keyReleased (KeyEvent event) {}

	/**
	 * Méthode appellée quand l'utilisateur appuie sur des touches :
	 * 		Ctrl+i : affiche ou cache les ids des controles
	 * 		Ctrl+t : affiche ou cache les types des controles
	 * @param event L'évenement appellant la méthode
	 */
	public void keyPressed (KeyEvent event)
	{
		switch ( e.getKeyCode() )
		{
			/* TOUCHE I */
			case KeyEvent.VK_I :
				// avec le modifier Ctrl
				if (KeyEvent.getKeyModifiersText(e.getModifiers()).equals("Ctrl"))
				{
					frame.toggleIds();
				}
			break;

			/* TOUCHE T */
			case KeyEvent.VK_T :
				// avec le modifier Ctrl
				if (KeyEvent.getKeyModifiersText(e.getModifiers()).equals("Ctrl"))
				{
					frame.toggleTypes();
				}
			break;
		}
	}

	/**
	 * Pas utilisé
	 * @param event L'événement
	 */
	public void keyTyped (KeyEvent event) {}
}
