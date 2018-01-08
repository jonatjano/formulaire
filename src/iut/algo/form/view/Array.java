package iut.algo.form.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;

/**
 * Zone de texte à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Array extends Control
{
	Object[] 	objects;
	JLabel 		labelL;


	public Array (String label, int width, int x, int y, Object[] objects)
	{
		super(label, width, x, y);
		this.objects = objects;


		/* Création des radio boutons */

		this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);

		this.panel.add( labelL );
	}

	public Array (String label, int x, int y, String[] choices)
	{
		this(label, Control.DFLT_WIDTH, x, y, choices);
	}

	/**
	 * Remet l'élément à son état initial
	 */
	public void reset ()
	{
		//this.checkbox.setSelected( this.baseValue );
	}
}
