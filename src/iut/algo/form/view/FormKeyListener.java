package iut.algo.form.view;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * KeyListener utilisé pour afficher et cacher les identifiants et types
 * @author Team Infotik
 * @version 2018-01-10
 */
public class FormKeyListener implements KeyListener
{
	/**
	 * la frame du programme
	 */
	private Frame frame;

	/**
	 * creer le listener
	 * @param  frame la frame
	 */
	public FormKeyListener(Frame frame)
	{
		this.frame = frame;
	}

	/**
	 * pas utilisée
	 * @param event l'événement
	 */
	public void keyReleased(KeyEvent event) {}

	/**
	 * methode appellée quand l'utilisateur appuie sur des touches
	 * 		s'il appui sur Ctrl+i : affiche ou cache les ids des controles
	 * 		s'il appui sur Ctrl+t : affiche ou cache les types des controles
	 * @param event l'évenement appellant la méthode
	 */
	public void keyPressed(KeyEvent event)
	{
		switch (event.getKeyCode())
		{
			// touche I
			case KeyEvent.VK_I :
				// avec le modifier Ctrl
				if (KeyEvent.getKeyModifiersText(event.getModifiers()).equals("Ctrl"))
				{
					frame.toggleIds();
				}
			break;

			// touche T
			case KeyEvent.VK_T :
				// avec le modifier Ctrl
				if (KeyEvent.getKeyModifiersText(event.getModifiers()).equals("Ctrl"))
				{
					frame.toggleTypes();
				}
			break;
		}
	}

	/**
	 * pas utilisée
	 * @param event l'événement
	 */
	public void keyTyped(KeyEvent event) {}
}
