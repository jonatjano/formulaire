package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
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
	private int nbR;

	private int minC;
	private int minL;
	private int maxC;
	private int maxL;

	private JPanel panelArray;
	private JPanel panelGauche;
	private Control value;

	private JPanel tableau;

	private JButton[][] tabButton;
	private JLabel[]    tabLabelLigne;
	private JLabel[]    tabLabelColonne;

	public Array (String label, String id, BaseType type, int width, int x, int y, int nbR, int nbC)
	{
		super(label, id, width, x, y);

		int tabWidth	= (Math.min(5, nbC) + 2) * 25;
		int tabHeight	= (Math.min(5, nbR) + 2) * 25;

		this.panel.setLayout( null );
		this.panel.setBounds(x, y, tabWidth + 150, tabHeight);
		this.panel.setBorder( BorderFactory.createLineBorder(Color.black) );

		/* Création du tableau */

		this.nbR	= nbR;
		this.nbC	= nbC;
		this.minL	= this.minC = 0;

		// Label
		// this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		// this.labelL.setForeground(Color.GRAY);

		// Tableau
		this.panelGauche	= new JPanel();
		this.panelGauche.setLayout( new BorderLayout() );
		this.panelGauche.setBounds( 0, 0, tabWidth, tabHeight );
		this.panelGauche.setBorder( BorderFactory.createLineBorder(Color.red) );

		this.panelArray		= new JPanel();
		this.panelArray.setBackground( Frame.obtainFormColor() );
		this.panelGauche.setBackground( Frame.obtainFormColor() );
		this.panelArray.setLayout( new BorderLayout() );

		if ( nbC > 5 )
		{
			if ( nbR > 5 )
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
				maxL = nbR;
				maxC = 5;
				tableau = new JPanel(new GridLayout(maxL+2,maxC+2));
				tabButton = new JButton[maxL][maxC];
				tabLabelLigne = new JLabel[maxL];
				tabLabelColonne = new JLabel[maxC];
			}
		}
		else
		{
			if ( nbR > 5 )
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
				maxL = nbR;
				maxC = nbC;
				tableau = new JPanel(new GridLayout(maxL+2,maxC+2));
				tabButton = new JButton[maxL][maxC];
				tabLabelLigne = new JLabel[maxL];
				tabLabelColonne = new JLabel[maxC];
			}
		}

		/* Ligne supérieure du tableau */
		tableau.add( new JLabel(nbR + "") );
		for( int cpt=1; cpt < maxC+2; cpt++)
		{
			JLabel label = new JLabel();
			//label.
			tableau.add( label );
		}

		for( int l = maxL-1 ; l >= 0 ; l-- )
		{
			JLabel jll = new JLabel(l + "");
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

		//this.panelGauche.add( labelL );
		this.panelArray.add( tableau );
		this.panelGauche.add( this.panelArray );

		


		/* Panel de droite (Elément interactible) */

		if (type == BaseType.Boolean)
			value = new Checkbox("value", "value", 0, 0);
		else
			value = new Text("value", "value", type, 0, 0);

		value.getPanel().setBounds( tabWidth, 0, width, Control.DFLT_HEIGHT );


		this.panel.add( this.panelGauche );
		this.panel.add( value.getPanel() );
	}

	public Array (String label, String id, BaseType type, int x, int y, int nbR, int nbC)
	{
		this(label, id, type, Control.DFLT_WIDTH, x, y, nbR, nbC);
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
