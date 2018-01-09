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
public class Array extends Control implements ActionListener
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
	private JPanel tableau;
	private JButton[][] tabButton;

	public Array (String label, String id, int width, int x, int y,Object[][] objects)
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
		panelFull = new Panel(new GridLayout(1,2));

			Object[] tabNul;
			if ( nbC > 1 )
			{

			}
			else
			{

			}

		this.panel = panelFull;
	}

	public Array (String label, String id, int x, int y, Object[][] objects)
	{
		this(label, id, Control.DFLT_WIDTH, x, y, nbC, nbL, objects);
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
