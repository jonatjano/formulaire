package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
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
	private Object[][] 	objects;
	private JLabel 		labelL;

	private int nbC;
	private int nbL;

	private int minC;
	private int minL;

	private JPanel panelFull;
	private JPanel panelGauche;
	private JPanel panelDroite;
	private Control value;

	private JPanel tableau;
	private JButton[][] tabButton;

	public Array (String label, String id, BaseType type, int width, int x, int y,Object[][] objects)
	{
		super(label, id, width, x, y);
		this.objects = objects;


		/* Création du tableau */
		this.nbL = objects.length;
		this.nbC = objects[0].length;
		this.minL = this.minC = 0;
		// Label
		this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setForeground(Color.GRAY);

		// Tableau
		panelFull = new JPanel(new GridLayout(1,2));

		tableau = new JPanel();
		Object[] tabNul;
		if ( nbC > 1 )
		{

		}
		else
		{

		}



		this.panelDroite = new JPanel();

		if (type == BaseType.Boolean)
			value = new Checkbox("value", "value", 0, 0);
		else
			value = new Text("value", "value", type, 0, 0);

		this.panelDroite.add(value.getPanel());
		this.panelFull.add(this.panelDroite);
		this.panel = panelFull;
	}

	public Array (String label, String id, BaseType type, int x, int y, Object[][] objects)
	{
		this(label, id, type, Control.DFLT_WIDTH, x, y, objects);
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
