package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;

/**
 * Zone de texte à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Buttons extends Control
{
	private ArrayList<JRadioButton> buttonList;


	public Buttons (String label, String id, int width, int x, int y, Object[] choices)
	{
		super(label, id, width, x, y);


		/* Création des radio boutons */

		this.panel.setLayout( new GridLayout(choices.length, 1, 0, 7) );
		this.panel.setBounds(x, y, width, choices.length * 20 + 40);

		// Création des radio boutons
		ButtonGroup bg 	= new ButtonGroup();
		this.buttonList	= new ArrayList<JRadioButton>();


		for (Object choice : choices)
		{
			if (choice != null)
			{
				String 			choiceValue = choice.toString();
				JRadioButton	button		= new JRadioButton( choiceValue );
				bg.add( button );
				this.buttonList.add(button);

				this.panel.add( button );
			}
		}

		// Si la liste de bouton en contient au moins un
		if ( buttonList.size() != 0)
		{
			// Crée et ajoute une bordure à titre
			TitledBorder titled = new TitledBorder(this.label);
	    	this.panel.setBorder(titled);

			this.buttonList.get(0).setSelected(true);
		}

	}

	public Buttons (String label, String id, int x, int y, Object[] choices)
	{
		this(label, id, Control.DFLT_WIDTH, x, y, choices);
	}


	/**
	 * Remet l'élément à son état initial
	 */
	@Override
	public void reset ()
	{
		this.buttonList.get(0).setSelected(true);
	}
}
