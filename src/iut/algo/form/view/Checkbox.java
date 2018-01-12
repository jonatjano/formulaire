package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Color;

/**
 * Case à cocher à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Checkbox extends Control
{
	/** Label décrivant le contôle à l'utilisateur */
	private JLabel 		labelL;
	/** Valeur de l'élément lors de sa création */
	private boolean		baseValue;


	/**
	 * Création d'une case à cocher, forcément associé à un booléen
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Checkbox (String label, String id, int width, int x, int y)
	{
		super(label, id, BaseType.Boolean, width, x, y);
		this.type 		= type;
		this.baseValue	= false;


		/* Création de la case à cocher */

		if (label != null)
		{
			this.labelL		= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
			this.labelL.setForeground(Color.GRAY);
			this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );

			this.panel.add( labelL );
		}

		this.compo	= new JCheckBox();


		this.panel.add( compo );
	}

	/**
	 * Création de l'élément case à cocher, forcément associé à un booléen
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Checkbox (String label, String id, int x, int y)
	{
		this(label, id, Control.DFLT_WIDTH, x, y);
	}

	/**
	 * Création de l'élément case à cocher sans label, forcément associé à un booléen
	 * @param id Identifiant unique de l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Checkbox (String id, int width, int x, int y)
	{
		this(null, id, width, x, y);
	}

	/**
	 * Réinitialise l'élément, le retournant au même état que lors de sa création
	 */
	@Override
	public void reset ()
	{
		((JCheckBox)this.compo).setSelected( this.baseValue );
	}

	/**
	 * Retourne la valeur contenue dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public Boolean getValue ()
	{
		return ((JCheckBox)this.compo).isSelected();
	}
	
	/**
	 * Modifie la valeur associée à l'élément
	 * @param newValue La nouvelle valeur associée à l'élément du formulaire
	 */
	@Override
	public void setValues (Object newValue)
	{
		((JCheckBox)this.compo).setSelected(newValue == null ? false : (Boolean)newValue);
	}
}
