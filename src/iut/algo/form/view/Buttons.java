package iut.algo.form.view;

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
	ArrayList<JRadioButton> buttonList;


	public Buttons (String label, int width, int x, int y, String[] choices)
	{
		super(label, width, x, y);


		/* Création des radio boutons */

		this.panel.setLayout( new GridLayout(choices.length, 1, 0, 7) );
		this.panel.setBounds(x, y, width, choices.length * 20 + 40);

		// Création des radio boutons
		ButtonGroup bg 	= new ButtonGroup();
		this.buttonList	= new ArrayList<JRadioButton>();

		for (String choice : choices)
		{
			JRadioButton button = new JRadioButton(choice);
			bg.add( button );
			this.buttonList.add(button);

			this.panel.add( button );
		}
		this.buttonList.get(0).setSelected(true);

		// Crée et ajoute une bordure à titre
		TitledBorder titled = new TitledBorder(this.label);
    	this.panel.setBorder(titled);
	}

	public Buttons (String label, int x, int y, String[] choices)
	{
		this(label, Control.DFLT_WIDTH, x, y, choices);
	}


	/**
	 * Remet l'élément à son état initial
	 */
	public void reset ()
	{
		this.buttonList.get(0).setSelected(true);
	}
}
