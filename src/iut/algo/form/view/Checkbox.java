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
	private JLabel 		labelL;
	private boolean		baseValue;


	public Checkbox (String label, String id, int width, int x, int y)
	{
		super(label, id, BaseType.Boolean, width, x, y);
		this.type 		= type;
		this.baseValue	= false;


		/* Création de la case à cocher */

		if (label != null)
		{
			this.labelL		= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
			this.labelL.setForeground(Color.GRAY);
			this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );

			this.panel.add( labelL );
		}

		this.compo	= new JCheckBox();


		this.panel.add( compo );
	}

	public Checkbox (String label, String id, int x, int y)
	{
		this(label, id, Control.DFLT_WIDTH, x, y);
	}

	public Checkbox (String id, int width, int x, int y)
	{
		this(null, id, width, x, y);
	}

	/**
	 * Remet l'élément à son état initial
	 */
	@Override
	public void reset ()
	{
		((JCheckBox)this.compo).setSelected( this.baseValue );
	}

	/**
	 * Retourne la valeur contenu dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public Boolean getValue ()
	{
		return ((JCheckBox)this.compo).isSelected();
	}
	
	public void setValues (Object obj)
	{
			((JCheckBox)this.compo).setSelected(obj == null ? false : (Boolean)obj);
	}
}
