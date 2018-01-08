package iut.algo.form.view;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Color;

/**
 * Zone de texte à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Dropdown extends Control
{
	JComboBox	dropdownD;
	JLabel 		labelL;
	Object[]	baseValues;


	@SuppressWarnings("unchecked")
	public Dropdown (String label, int width, int x, int y, Object[] choices)
	{
		super(label, width, x, y);
		this.baseValues = choices;

		/* Création de la liste déroulante */

		this.labelL		= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);
		this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );
		this.dropdownD	= new JComboBox(choices);
		this.dropdownD.setPreferredSize( new Dimension(width, this.panel.getSize().height) );
		this.dropdownD.setEditable(true);


		this.panel.add( labelL );
		this.panel.add( dropdownD );
	}

	public Dropdown (String label, int x, int y, Object[] choices)
	{
		this(label, Control.DFLT_WIDTH, x, y, choices);
	}


	/**
	 * Remet l'élément à son état initial
	 */
	public void reset ()
	{
		// Itère à travers tous les éléments pour rétablir leur valeur initiale
		for (int i = 0; i < this.dropdownD.getItemCount(); i++)
		{
			Object obj = this.dropdownD.getItemAt(i);
			obj = baseValues[i];
		}

		// Enfin, la première valeur est celle mise en valeur
		this.dropdownD.setSelectedIndex(0);
	}
}
