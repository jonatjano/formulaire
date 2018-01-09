package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.*;

/**
 * Elément à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public abstract class Control
{
	public final static int DFLT_WIDTH	= 150;
	public final static int LABEL_WIDTH = 100;

	/** Position sur l'axe des abscisses de l'élément */
	protected int 		x;
	/** Position sur l'axe des ordonnées de l'élément */
	protected int 		y;

	//protected String	id;
	protected BaseType	type;
	protected String	label;

	protected JPanel	panel;


	public Control (String label, int width, int x, int y)
	{
		this.label = label;

		this.panel = new JPanel();
		this.panel.setBounds(x, y, width + Control.LABEL_WIDTH + 20, 25); //this.panel.getPreferredSize().height);
		this.panel.setBackground( Frame.obtainFormColor() );
		this.panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
	}

	/**
	 * Renvoie le panel contenant l'élément
	 * @return Panel de l'élément
	 */
	public JPanel getPanel ()
	{
		return this.panel;
	}

	/**
	 * Remet l'élément à son état initial
	 */
	public abstract void reset ();
}
