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
	private int maxC;
	private int maxL;

<<<<<<< HEAD
	public Array (String label, String id, int width, int x, int y, Object[][] objects)
=======
	private JPanel panelFull;
	private JPanel panelGauche;
	private JPanel panelDroite;
	private JPanel tableau;

	private JButton[][] tabButton;
	private JLabel[]    tabLabelLigne;
	private JLabel[]    tabLabelColonne;

	public Array (String label, String id, int width, int x, int y,Object[][] objects)
>>>>>>> 442aeb01efe2946ae8aec53233b3121f25028ce5
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

			if ( nbC > 5 )
			{
				if ( nbL > 5 )
				{
					maxL = 5;
					maxC = 5;
					tableau = new JPanel(new GridLayout(maxL+2,maxC+2));
					tabButton = new JButton[maxL][maxC];
					tabLabelLigne = new JLabel[maxL];
					tabLabelColonne = new JLabel[maxC];
				}
				else
				{
					maxL = nbL;
					maxC = 5;
					tableau = new JPanel(new GridLayout(maxL+2,maxC+2));
					tabButton = new JButton[maxL][maxC];
					tabLabelLigne = new JLabel[maxL];
					tabLabelColonne = new JLabel[maxC];
				}
			}
			else
			{
				if ( nbL > 5 )
				{
					maxL = 5;
					maxC = nbC;
					tableau = new JPanel(new GridLayout(maxL+2,maxC+2));
					tabButton = new JButton[maxL][maxC];
					tabLabelLigne = new JLabel[maxL];
					tabLabelColonne = new JLabel[maxC];
				}
				else
				{
					maxL = nbL;
					maxC = nbC;
					tableau = new JPanel(new GridLayout(maxL+2,maxC+2));
					tabButton = new JButton[maxL][maxC];
					tabLabelLigne = new JLabel[maxL];
					tabLabelColonne = new JLabel[maxC];
				}
			}

			tableau.add(new JLabel(nbL + ""));
			for( int cpt=1; cpt < maxC+2; cpt++)
				tableau.add(new JLabel(""));

			for( int l = maxL-1 ; l >= 0 ; l-- )
			{
				JLabel jll = new JLabel(l+"");
				tabLabelLigne[l] = jll;
				tableau.add(jll);
				for ( int c = 0 ; c < maxC ; c++ )
				{
					JButton j = new JButton("");
					tabButton[l][c] = j;
					tableau.add(j);
				}
				tableau.add(new JLabel(""));
			}

			tableau.add(new JLabel(""));
			for( int c = 0 ; c < maxC ; c++ )
			{
				JLabel jlc = new JLabel(c+"");
				tabLabelColonne[c] = jlc;
				tableau.add(jlc);
			}
			tableau.add(new JLabel(""));

<<<<<<< HEAD
		this.panel.add( labelL );
=======
		this.panel = panelFull;
>>>>>>> 442aeb01efe2946ae8aec53233b3121f25028ce5
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
