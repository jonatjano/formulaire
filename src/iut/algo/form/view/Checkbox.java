package iut.algo.form.view;

import iut.algo.form.job.BaseType;

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
	private JCheckBox	checkbox;
	private JLabel 		labelL;
	private boolean		baseValue;


	public Checkbox (String label, String id, int width, int x, int y)
	{
		super(label, id, BaseType.Boolean, width, x, y);
		this.type 		= type;
		this.baseValue	= false;


		/* Création de la case à cocher */

		this.labelL		= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);
		this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );

		this.checkbox	= new JCheckBox();

		this.panel.add( labelL );
		this.panel.add( checkbox );
	}

	public Checkbox (String label, String id, int x, int y)
	{
		this(label, id, Control.DFLT_WIDTH, x, y);
	}

	/**
	 * Remet l'élément à son état initial
	 */
	@Override
	public void reset ()
	{
		this.checkbox.setSelected( this.baseValue );
	}

	@Override
	public Boolean getValues()
	{
		return checkbox.isSelected();
	}
}
