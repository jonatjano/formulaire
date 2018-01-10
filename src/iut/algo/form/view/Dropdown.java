package iut.algo.form.view;

import iut.algo.form.job.BaseType;

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
	private JComboBox	dropdownD;
	private JLabel 		labelL;
	private Object[]	baseValues;


	@SuppressWarnings("unchecked")
	public Dropdown (String label, String id, int width, int x, int y, Object[] choices)
	{
		super(label, id, BaseType.String, width, x, y);
		this.type 		= type;
		this.baseValues = choices;


		/* Création de la liste déroulante */

		this.labelL		= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);
		this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );
		this.dropdownD	= new JComboBox(choices);
		this.dropdownD.setPreferredSize( new Dimension(width, (int) (this.panel.getSize().height - (Control.DFLT_HEIGHT / 5f))) );
		this.dropdownD.setEditable(true);


		this.panel.add( labelL );
		this.panel.add( dropdownD );
	}

	public Dropdown (String label, String id, int x, int y, Object[] choices)
	{
		this(label, id, Control.DFLT_WIDTH, x, y, choices);
	}


	/**
	 * Remet l'élément à son état initial
	 */
	@Override
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

	/**
	 * Retourne la valeur contenu dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public Object obtainValue ()
	{
		return null;
	}
}
