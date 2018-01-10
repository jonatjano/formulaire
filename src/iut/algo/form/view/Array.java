package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import java.awt.Component;
import java.awt.Font;
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
	private static final int minCol = 1;
	private static final int minRow = 1;
	private static final int maxCol = 5;
	private static final int maxRow = 5;

	private Object[][] 	objects;
	private JLabel 		labelL;

	private int 		nbC;
	private int 		nbR;

	private JPanel 		arrayP;
	private Control 	cellControl;

	private Component[][] tabButtons;


	public Array (String label, String id, BaseType type, int width, int x, int y, int nbR, int nbC)
	{
		super(label, id, type, width, x, y);
		this.type	= type;
		this.nbR	= nbR;
		this.nbC	= nbC;


		// Les dimensions du tableau
		int tabWidth	= (Math.min(5, nbC) + 2) * 25;
		int tabHeight	= (Math.min(5, nbR) + 2) * 25;

		this.panel.setLayout( null );
		this.panel.setBounds( x, y, tabWidth + width + Control.LABEL_WIDTH, tabHeight );


		/*~~~~~~~~~~~~~~~~~~~~~*/
		/* Création du tableau */
		/*~~~~~~~~~~~~~~~~~~~~~*/

		/*  Label */
		this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setBounds( 0, 0, Control.LABEL_WIDTH, Control.DFLT_HEIGHT );
		this.labelL.setForeground(Color.GRAY);

		this.panel.add( labelL );


		/* Tableau */
		int clampedCol 		= Math.max( 0, Math.min(Array.maxCol, nbC) );
		int clampedRow 		= Math.max( 0, Math.min(Array.maxRow, nbR) );

		this.arrayP			= new JPanel( new GridLayout(clampedRow + 2, clampedCol + 2) );
		this.arrayP.setBounds( Control.LABEL_WIDTH, 0, tabWidth, tabHeight );
		this.arrayP.setBackground( Frame.obtainFormColor() );

		tabButtons 			= new Component[clampedRow][clampedCol];

		this.panel.add( this.arrayP );


		/* Création du tableau logique */
		tabButtons = new Component[clampedRow][clampedCol];
		for (int i = 0; i < tabButtons.length; i++)
			for (int j = 0; j < tabButtons[i].length; j++)
				tabButtons[i][j] = new JButton();

		
		Font baseFont	= this.panel.getFont();
		Font newFont	= baseFont.deriveFont(baseFont.getStyle() | Font.BOLD);
		
		/* Création du tableau sur l'interface */
		for (int i = -1; i < clampedRow + 1; i++)
		{
			boolean inRowBounds = i >= 0 && i < clampedRow;

			this.arrayP.add( new JLabel((inRowBounds ? "" + (clampedRow - i - 1) : "")) );

			/* CORPS */
			for (int j = 0; j < clampedCol; j++)
			{
				boolean inColBounds = j >= 0 && j < clampedCol;

				if (inColBounds)
				{
					if (inRowBounds)	this.arrayP.add( tabButtons[i][j] );
					else				this.arrayP.add( new JLabel(((i < 0) ? "" : "" + j), SwingConstants.CENTER) );
				}
				else
					this.arrayP.add( new JLabel() );
			}

			// Ajoute des label vide pour combler les de la bordure
			this.arrayP.add( new JLabel() );
		}

		
		/*~~~~~~~~~~~~~~~~~~~~~~~~~*/
		/*  Panel de modification  */
		/*~~~~~~~~~~~~~~~~~~~~~~~~~*/

		if (type == BaseType.Boolean)	this.cellControl = new Checkbox("value", "value", 0, 0);
		else							this.cellControl = new Text("value", "value", type, 0, 0);

		this.cellControl.getPanel().setBorder( BorderFactory.createLineBorder(Color.red) );
		this.cellControl.getPanel().setBounds( tabWidth + Control.LABEL_WIDTH, 0, width, Control.DFLT_HEIGHT );
		this.panel.add( cellControl.getPanel() );
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
