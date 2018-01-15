package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import iut.algo.form.job.Language;
/**
 * Radio boutons à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Buttons extends Control
{
	/** List des radio boutons affichés contenant les valeurs choisies par l'utilisateur */ //TODO
	private HashMap<Integer, JRadioButton> mapButton;
	/** Groupe de bouton regroupant l'ensemble des radio boutons de l'élément du formulaire */
	private ButtonGroup				buttonGroup;


	/**
	 * Création d'une liste de radio boutons, où un seul est sélectionnable à la fois, forcément associé à une liste de
	 * chaînes de caractère
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
 	 * @param width Largeur de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @param mapOrdObj Valeurs d'origine associées à l'élément lors de sa création
	 * @return L'élément créé
	 */ //TODO
	public Buttons (String label, String id, int width, int x, int y, HashMap<Integer, Object> mapOrdObj, Language language)
	{
		super(label, id, BaseType.Integer, width, x, y, language);
		this.type = type;


		/* Création des radio boutons */

		this.panel.setLayout( new BoxLayout(this.panel, BoxLayout.Y_AXIS) );
		this.panel.setBounds(x, y, width + 20, Control.DFLT_HEIGHT);

		// Création des radio boutons
		this.setValue(mapOrdObj);

		// Si la liste de bouton en contient au moins un, le contrôle est créé
		// Sinon, le panel est vide
		if ( mapButton.size() != 0)
		{
			// Crée et ajoute une bordure à titre
			TitledBorder titled = new TitledBorder(this.label);
	    	this.panel.setBorder(titled);

			//this.buttonList.get(0).setSelected(true);
		}
	}

	/**
	 * Création d'une liste de radio boutons, où un seul est sélectionnable à la fois, forcément associé à une liste de
	 * chaînes de caractère
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @param mapOrdObj Valeurs d'origine associées à l'élément lors de sa création
	 * @return L'élément créé
	 */ //TODO
	public Buttons (String label, String id, int x, int y, HashMap<Integer, Object> mapOrdObj, Language language)
	{
		this( label, id, Control.DFLT_WIDTH, x, y, mapOrdObj, language );
	}


	/**
	 * Réinitialise l'élément, le retournant au même état que lors de sa création
	 */
	@Override
	public void reset ()
	{
		Set<Integer> setKeyBut = mapButton.keySet();

		int ordinalMin = Integer.MAX_VALUE;
		for (int ordiMinTemp : setKeyBut)
			if ( ordiMinTemp < ordinalMin )
				ordinalMin = ordiMinTemp;

		mapButton.get(ordinalMin).setSelected(true);
	}

	/**
	 * Retourne la valeur contenue dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public Integer getValue ()
	{
		for (Integer i : this.mapButton.keySet())
			if ( this.mapButton.get(i).isSelected() )
				return i;

		return null;
	}


	/**
	 * Modifie la valeur associée à l'élément
	 * @param newValue La nouvelle valeur associée à l'élément du formulaire
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean setValue (Object newValue)
	{
		// on s'assure que newValue est un tableau à une dimension
		if (newValue != null && newValue instanceof HashMap)
		{
			HashMap<Integer, Object> mapOrdObj	= (HashMap<Integer, Object>) newValue;
			this.buttonGroup		= new ButtonGroup();

			if (this.mapButton != null)
			{
				for (JRadioButton button : mapButton.values())
					this.panel.remove( button );
			}

			this.mapButton	= new HashMap<Integer, JRadioButton>();
			this.panel.setBounds(x, y, this.panel.getSize().width, mapOrdObj.size() * 18 + 45);


			for (Integer i : mapOrdObj.keySet())
			{
				if (mapOrdObj.get(i) != null)
				{
					String 			valueStr	= mapOrdObj.get(i).toString();
					JRadioButton	button		= new JRadioButton( valueStr );

					if (this.buttonGroup.getButtonCount() == 0)
						button.setSelected(true);

					this.buttonGroup.add( button );
					this.mapButton.put( i, button );

					this.panel.add( button );
				}
			}
			return true;
		}
		return false;
	}
}
