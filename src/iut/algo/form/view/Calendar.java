package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import java.awt.Component;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.Date;

/**
 * Zone de texte ouvrant un calendrier à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Calendar extends Control
{
	/** Label décrivant le contôle à l'utilisateur */
	private JLabel			labelL;
	private DateTextField	date;

	/**
	 * Création d'un calendrier, permettant à l'utilisateur de choisir une date de son choix, et forcément
	 * associé au type chaine
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Calendar (String label, String id, int width, int x, int y)
	{
		super( label, id, BaseType.String, width, x, y );


		/* Création du label */

		this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground( Color.GRAY );
		this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );

		this.panel.add( labelL );


		/* Création de la date */

		this.date = new DateTextField();
		this.date.setPreferredSize( new Dimension(width, (int) (this.panel.getSize().height - (Control.DFLT_HEIGHT / 5f))) );

		this.panel.add( date );
	}

	/**
	 * Création d'un calendrier, permettant à l'utilisateur de choisir une date de son choix, et forcément
	 * associé au type chaine
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @return L'élément créé
	 */
	public Calendar (String label, String id, int x, int y)
	{
		this(label,id, Control.DFLT_WIDTH,x,y);
	}

	/**
	 * Réinitialise l'élément, le retournant au même état que lors de sa création
	 */
	@Override
	public void reset ()
	{
		date.setDate( new Date() );
	}

	/**
	 * Retourne la valeur contenue dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public String getValue ()
	{
		return date.getDate().toString();
	}

	/**
	 * Modifie la valeur associée à l'élément
	 * @param newValue La nouvelle valeur associée à l'élément du formulaire
	 */
	@Override
	public boolean setValue (Object newValue)
	{
		// TODO
		return false;
	}
}
