package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;

import java.awt.Font;
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
	public final static int DFLT_HEIGHT	= 30;
	public final static int LABEL_WIDTH = 100;

	/** Position sur l'axe des abscisses de l'élément */
	protected int 		x;
	/** Position sur l'axe des ordonnées de l'élément */
	protected int 		y;

	protected String	id;
	protected BaseType	type;
	protected String	label;

	protected JPanel	panel,
						idPanel,
						typePanel;


	/**
	 * Création de la base d'un élément du formulaire
	 */
	public Control (String label, String id, BaseType type, int width, int x, int y)
	{
		this.label	= label;
		this.type	= type;
		this.id		= id;

		this.panel	= new JPanel();
		this.panel.setBounds(x, y, width + Control.LABEL_WIDTH + 20, Control.DFLT_HEIGHT); //this.panel.getPreferredSize().height);
		this.panel.setBackground( Frame.obtainFormColor() );
		this.panel.setLayout( new FlowLayout(FlowLayout.LEFT) );

		Font font 	= this.panel.getFont();


		/* Création du panel d'identifiant */

		this.idPanel	= new JPanel();
		this.idPanel.setBounds(x - 25, y, 25, Control.DFLT_HEIGHT);
		this.idPanel.setLayout( new BorderLayout() );
		this.idPanel.setBorder( BorderFactory.createLineBorder(Color.black) );
		this.idPanel.setBackground( new Color(0.86f, 0.34f, 0.53f) );

		JLabel	idL		= new JLabel(id);
		idL.setFont( font.deriveFont(font.getStyle() | Font.BOLD) );

		this.idPanel.add( idL );


		/* Création du panel de type */

		this.typePanel	= new JPanel();
		this.typePanel.setBounds(width + Control.LABEL_WIDTH + 50, y, 100, Control.DFLT_HEIGHT);
		this.typePanel.setLayout( new BorderLayout() );
		this.typePanel.setBackground( new Color(0.60f, 0.90f, 0.35f) );
		this.typePanel.setBorder( new CompoundBorder(BorderFactory.createLineBorder(Color.black), new EmptyBorder(3,3,3,3)) );

		JLabel typeL 	= new JLabel( type.getValue( Frame.language ) );
		typeL.setFont( font.deriveFont(font.getStyle() | Font.BOLD) );

		this.typePanel.add( typeL );

		
		// Par défaut, cache les identifiants et les types
		this.idPanel.setVisible(true);
		this.typePanel.setVisible(true);
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
	 * @return Panel d'identifiant de l'élément
	 */
	public JPanel getIdPanel ()
	{
		return this.idPanel;
	}

	/**
	 * Renvoie le panel contenant le type de l'élément
	 * @return Panel de type de l'élément
	 */
	public JPanel getTypePanel ()
	{
		return this.typePanel;
	}


	public void toggleId ()
	{
		this.idPanel.setVisible( !this.idPanel.isVisible() );
	}

	public void toggleType ()
	{
		this.typePanel.setVisible( !this.typePanel.isVisible() );
	}

	/**
	 * Remet l'élément à son état initial
	 */
	public abstract void reset ();
}
