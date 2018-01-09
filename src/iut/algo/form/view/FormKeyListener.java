package iut.algo.form.view;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class FormKeyListener implements KeyListener
{
	private Frame frame;

	public FormKeyListener(Frame frame)
	{
		this.frame = frame;
	}

	public void keyReleased(KeyEvent event) {}
	public void keyPressed(KeyEvent event)
	{
		switch (event.getKeyCode())
		{
			default : System.out.println("pressed : " + event.getKeyCode());
		}
	}

	public void keyTyped(KeyEvent event) {}
}
