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

/**
 * Radio boutons à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Buttons extends Control
{
	/** List des radio boutons affichés contenant les valeurs choisies par l'utilisateur */
	private ArrayList<JRadioButton> buttonList;
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
	 * @param choices Valeurs d'origine associées à l'élément lors de sa création
	 * @return L'élément créé
	 */
	public Buttons (String label, String id, int width, int x, int y, Object[] choices)
	{
		super(label, id, BaseType.String, width, x, y);
		this.type = type;


		/* Création des radio boutons */

		this.panel.setLayout( new BoxLayout(this.panel, BoxLayout.Y_AXIS) );
		this.panel.setBounds(x, y, width + 20, Control.DFLT_HEIGHT);

		// Création des radio boutons
		this.setValue(choices);

		// Si la liste de bouton en contient au moins un, le contrôle est créé
		// Sinon, le panel est vide
		if ( buttonList.size() != 0)
		{
			// Crée et ajoute une bordure à titre
			TitledBorder titled = new TitledBorder(this.label);
	    	this.panel.setBorder(titled);

			this.buttonList.get(0).setSelected(true);
		}
	}

	/**
	 * Création d'une liste de radio boutons, où un seul est sélectionnable à la fois, forcément associé à une liste de
	 * chaînes de caractère
	 * @param label Label à afficher à gauche de l'élément
	 * @param id Identifiant unique de l'élément
	 * @param x Coordonnée sur l'axe des abscisses de l'élément
	 * @param y Coordonnée sur l'axe des ordonnées de l'élément
	 * @param choices Valeurs d'origine associées à l'élément lors de sa création
	 * @return L'élément créé
	 */
	public Buttons (String label, String id, int x, int y, Object[] choices)
	{
		this( label, id, Control.DFLT_WIDTH, x, y, choices );
	}


	/**
	 * Réinitialise l'élément, le retournant au même état que lors de sa création
	 */
	@Override
	public void reset ()
	{
		this.buttonList.get(0).setSelected(true);
	}

	/**
	 * Retourne la valeur contenue dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public String getValue ()
	{
		for (JRadioButton button : this.buttonList)
			if ( button.isSelected() )
				return button.getText();

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
		if (newValue != null && newValue.getClass().isArray() && !((Object[])(newValue))[0].getClass().isArray())
		{
			Object[] valuesToSet	= (Object[]) newValue;
			this.buttonGroup		= new ButtonGroup();

			if (this.buttonList != null)
			{
				for (JRadioButton button : buttonList)
				this.panel.remove( button );
			}

			this.buttonList	= new ArrayList<JRadioButton>();
			this.panel.setBounds(x, y, this.panel.getSize().width, valuesToSet.length * 18 + 45);


			for (Object valueToSet : valuesToSet)
			{
				if (valueToSet != null)
				{
					String 			valueStr	= valueToSet.toString();
					JRadioButton	button		= new JRadioButton( valueStr );

					this.buttonGroup.add( button );
					this.buttonList.add( button );

					this.panel.add( button );
				}
			}
			return true;
		}
		return false;
	}
}
