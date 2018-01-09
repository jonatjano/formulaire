package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;

/**
 * Zone de texte à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Text extends Control
{
	private Component	textF;
	private JLabel 		labelL;
	private String		baseValue;


	/**
	 * Crée un objet Texte, qui comprend un label et une zone de texte avec laquelle il est possible d'interargir
	 */
	public Text (String label, String id, BaseType type, int width, int x, int y)
	{
		super(label, id, width, x, y);
		this.type 		= type;
		this.baseValue	= "";


		/* Création de la zone texte */

		this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);
		this.labelL.setPreferredSize( new Dimension(Control.LABEL_WIDTH, this.panel.getSize().height) );

		SpinnerModel spinnerModel;

		// Vérification du type de la zone de texte
		switch (type)
		{
			case Int:
				spinnerModel = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
				this.textF	= new JSpinner(spinnerModel);
				break;

			case Double:
				spinnerModel = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0.1f);
				this.textF	= new JSpinner(spinnerModel);
				break;

			case String:
				this.textF	= new JTextField();
				break;
		}

		this.textF.setPreferredSize( new Dimension(width, this.panel.getSize().height) );


		this.panel.add( labelL );
		this.panel.add( textF );
	}

	/**
	 * Crée un objet Texte, qui comprend un label et une zone de texte avec laquelle il est possible d'interargir
	 */
	public Text (String label, String id, BaseType type, int x, int y)
	{
		this(label, id, type, Control.DFLT_WIDTH, x, y);
	}


	/**
	 * Remet l'élément à son état initial
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void reset ()
	{
		if (this.textF instanceof JTextField)
		{
			JTextField field = (JTextField) (this.textF);
			field.setText( baseValue );
		}
		else
		{
			JSpinner field = (JSpinner) (this.textF);
			field.setValue(0);
		}
	}
}
