package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
	public final static int DFLT_HEIGHT	= 35;
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

		this.x		= x;
		this.y		= y;


		this.panel	= new JPanel();
		this.panel.setBounds(x, y, width + Control.LABEL_WIDTH + 20, Control.DFLT_HEIGHT);
		this.panel.setBackground( Frame.obtainFormColor() );
		this.panel.setLayout( new FlowLayout(FlowLayout.LEFT, 0, 0) );
		//this.panel.setBorder( BorderFactory.createLineBorder(Color.red) );

		Font font 	= this.panel.getFont();


		/* Création du panel d'identifiant */

		this.idPanel	= new JPanel();
		this.idPanel.setBounds(x - Control.DFLT_HEIGHT, y, Control.DFLT_HEIGHT, Control.DFLT_HEIGHT);
		this.idPanel.setLayout( new BorderLayout() );
		this.idPanel.setBorder( BorderFactory.createLineBorder(Color.black) );
		this.idPanel.setBackground( new Color(0.86f, 0.34f, 0.53f) );

		JLabel	idL		= new JLabel(id, SwingConstants.CENTER);
		idL.setFont( font.deriveFont(font.getStyle() | Font.BOLD) );

		this.idPanel.add( idL );


		/* Création du panel de type */

		this.typePanel	= new JPanel();
		this.typePanel.setBounds( x + this.panel.getSize().width, y, 100, Control.DFLT_HEIGHT );
		this.typePanel.setLayout( new BorderLayout() );
		this.typePanel.setBackground( new Color(0.60f, 0.90f, 0.35f) );
		this.typePanel.setBorder( new CompoundBorder(BorderFactory.createLineBorder(Color.black), new EmptyBorder(3,3,3,3)) );

		JLabel typeL 	= new JLabel( type.getValue( Frame.language ) );
		typeL.setFont( font.deriveFont(font.getStyle() | Font.BOLD) );

		this.typePanel.add( typeL );


		// Par défaut, cache les identifiants et les types
		this.idPanel.setVisible(false);
		this.typePanel.setVisible(false);
	}

	/**
	 * Déplace l'élément à la position passée en paramètre
	 * @param x Abscisse de la nouvelle position
	 * @param y Ordonnée de la nouvelle position
	 */
	public void move (int x, int y)
	{
		this.x = x;
		this.y = y;

		this.panel.setBounds(this.x, this.y, this.panel.getSize().width, this.panel.getSize().height);
		this.typePanel.setBounds( x + this.panel.getSize().width, y, 100, Control.DFLT_HEIGHT );
		this.idPanel.setBounds( x - Control.DFLT_HEIGHT, y, Control.DFLT_HEIGHT, Control.DFLT_HEIGHT );
	}


	/*-------------------*/
	/*      Toggles      */
	/*-------------------*/

	/**
	 * Inverse l'état de l'affichage de l'identifiant
	 */
	public void toggleId ()
	{
		this.idPanel.setVisible( !this.idPanel.isVisible() );
	}

	/**
	 * Inverse l'état de l'affichage du type
	 */
	public void toggleType ()
	{
		this.typePanel.setVisible( !this.typePanel.isVisible() );
	}


	/*-------------------*/
	/*      GETTERS      */
	/*-------------------*/

	public int getX ()
	{
		return this.x;
	}

	public int getY ()
	{
		return this.y;
	}

	/**
	 * Renvoie l'id de l'élément du formulaire
	 * @return Identifiant de l'élément
	 */
	public String getId ()
	{
		return this.id;
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

	/**
	 * Renvoie la hauteur totale de l'élément
	 * @return La hauteur de l'élément
	 */
	public int obtainHeight ()
	{
		return this.panel.getSize().height;
	}

	/**
	 * Renvoie la largeur totale de l'élément
	 * @return La largeur de l'élément
	 */
	public int obtainWidth ()
	{
		return this.panel.getSize().width;
	}

	/**
	 * Renvoie le type de donnée du control
	 * @return le BaseType du control
	 */
	public BaseType getType ()
	{
		return this.type;
	}


	/**
	 * Remet l'élément à son état initial
	 */
	public abstract void reset ();

	/**
	 * Retourne la valeur contenu dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	public abstract Object getValue ();
}
