package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.BorderFactory;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;

/**
 * Label seul à afficher dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Label extends Control
{
	/**
	 * Création d'un label seul, avec la largeur de base
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Label (String label, String id, int width, int x, int y)
	{
		super(label, id, BaseType.String, width, x, y);
		this.type 	= BaseType.String;
		this.label	= label;

		this.panel.setBounds(x, y, width + 20, Control.DFLT_HEIGHT);


		/* Création du label */

		this.compo	= new JLabel( this.label, SwingConstants.LEFT );
		this.compo.setForeground( Color.GRAY );
		this.compo.setPreferredSize( new Dimension(width, Control.DFLT_HEIGHT) );

		this.panel.add( this.compo );
	}

	/**
	 * Création d'un label seul, avec la largeur de base
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Label (String label, String id, int x, int y)
	{
		this(label, id, Control.DFLT_WIDTH, x, y);
	}


	/**
	 * Réinitialise l'élément, le retournant au même état que lors de sa création
	 */
	@Override
	public void reset ()
	{
		// Ne fait rien du tout, la valeur reste inchangée
	}

	/**
	 * Retourne la valeur contenue dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public Object getValue ()
	{
		return this.label;
	}

	/**
	 * Modifie la valeur associée à l'élément
	 * @param newValue La nouvelle valeur associée à l'élément du formulaire
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean setValue (Object newValue)
	{
		// newValue n'est pas null est n'est pas un tableau
		if (newValue != null && !newValue.getClass().isArray())
		{
			String valueToSet = newValue.toString();
			((JLabel) this.compo).setText(valueToSet);
			return true;
		}
		return false;
	}
}
