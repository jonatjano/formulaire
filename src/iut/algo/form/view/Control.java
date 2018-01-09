package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

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
	protected JPanel	idPanel;


	/**
	 * Création de la base d'un élément du formulaire
	 */
	public Control (String label, String id, int width, int x, int y)
	{
		this.label		= label;

		this.panel		= new JPanel();
		this.panel.setBounds(x, y, width + Control.LABEL_WIDTH + 20, 25); //this.panel.getPreferredSize().height);
		this.panel.setBackground( Frame.obtainFormColor() );
		this.panel.setLayout( new FlowLayout(FlowLayout.LEFT) );

		this.idPanel	= new JPanel();
		this.idPanel.setBounds(x - 20, y, 20, 20);
		this.idPanel.setLayout( new BorderLayout() );
		this.idPanel.setBackground( Color.red );

		JLabel idL		= new JLabel(id);
		this.idPanel.add( idL );
		//this.idPanel.setVisible(true);
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
	 * Renvoie le panel contenant l'id de l'élément
	 * @return Panel de l'élément
	 */
	public JPanel getIdPanel ()
	{
		return this.panel;
	}

	/**
	 * Remet l'élément à son état initial
	 */
	public abstract void reset ();
}
