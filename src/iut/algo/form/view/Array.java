package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

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
	private Object[] 	objects;
	private JLabel 		labelL;

	
	public Array (String label, String id, int width, int x, int y, Object[][] objects)
	{
		super(label, id, width, x, y);
		this.objects = objects;


		/* Création du tableau */

		// Label
		this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);

		// Tableau
		JTable table = new JTable(objects, new Object[] {"Truc", "machin", "bidule"});
		JScrollPane scrollPane = new JScrollPane(table);	// Crée un scrollpane auquel est ajouté le tableau

		this.panel.add( labelL );
        this.panel.add( scrollPane );
	}

	public Array (String label, String id, int x, int y, Object[][] choices)
	{
		this(label, id, Control.DFLT_WIDTH, x, y, choices);
	}

	/**
	 * Remet l'élément à son état initial
	 */
	@Override
	public void reset ()
	{
		//this.checkbox.setSelected( this.baseValue );
	}
}