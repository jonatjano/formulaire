package iut.algo.form.view;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Color;

/**
 * Zone de texte à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Checkbox extends Control
{
	JCheckBox	checkbox;
	JLabel 		labelL;
	boolean		baseValue;


	public Checkbox (String label, int width, int x, int y)
	{
		super(label, width, x, y);
		this.baseValue = false;


		/* Création de la case à cocher */

		this.labelL		= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);
		this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );

		this.checkbox	= new JCheckBox();

		this.panel.add( labelL );
		this.panel.add( checkbox );
	}

	public Checkbox (String label, int x, int y)
	{
		this(label, Control.DFLT_WIDTH, x, y);
	}

	/**
	 * Remet l'élément à son état initial
	 */
	public void reset ()
	{
		this.checkbox.setSelected( this.baseValue );
	}
}
