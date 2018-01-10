package iut.algo.form.view;

import iut.algo.form.job.BaseType;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Component;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Zone de texte à placer dans le formulaire
 * @author Team Infotik
 * @version 2018-01-08
 */
public class Array extends Control
{
	class ArrayListener implements ActionListener
	{


		public void actionPerformed (ActionEvent e)
		{
			if (e.getSource() instanceof JButton)
			{
				String[] pos = e.getActionCommand().split(";");

				int row = Integer.parseInt( pos[0] );
				int col = Integer.parseInt( pos[1] );
				
				// Enregistrement dans le tableau de valeurs
				Object value = valueControl.getValues();
				tabValues[oriR + prevR][oriC + prevC] = value;
				
				int deltaR = 0;
				int deltaC = 0;
				
				switch (row)
				{
					case 0 :
						deltaR = -1;
						break;
					case 4 :
						deltaR = 1;
						break;
				}
				switch (col)
				{
					case 0 :
						deltaC = -1;
						break;
					case 4 :
						deltaC = 1;
						break;
				}
				
				
				int oriRTemp = oriR;
				int oriCTemp = oriC;
				// Décale le tableau
				shiftDisplay( deltaR, deltaC);
				
				if (oriR - oriRTemp == -1)
					row++;
				if (oriR - oriRTemp == 1)
					row--;
				if (oriC - oriCTemp == -1)
					col++;
				if (oriC - oriCTemp == 1)
					col--;
					
				for (int i = 0; i < tabButtons.length; i++)
				{
					tabButtons[i][prevC].setBackground(normalColor);
				}
				for (int i = 0; i < tabButtons[prevR].length; i++)
				{
					tabButtons[prevR][i].setBackground(normalColor);
				}
				
				for (int i = 0; i < tabButtons.length; i++)
				{
					tabButtons[i][col].setBackground(selectedRowColor);
				}
				for (int i = 0; i < tabButtons[row].length; i++)
				{
					tabButtons[row][i].setBackground(selectedColColor);
				}
				
				tabButtons[row][col].setBackground(selectedCellColor);
				
				prevR = row;
				prevC = col;
				
				

				// Change le focus pour le mettre sur l'élément à modifier
			}
		}
	}


	private static final int minCol = 1;
	private static final int minRow = 1;
	private static final int maxCol = 5;
	private static final int maxRow = 5;

	private static final int gapX	= 10;
	
	private static final Color normalColor = new Color(255,255,255);
	private static final Color selectedRowColor = new Color(0,0,0);
	private static final Color selectedColColor = new Color(0,0,0);
	private static final Color selectedCellColor = new Color(255,0,0);

	private JLabel 			labelL;

	private int				oriC;
	private int				oriR;
	
	private int prevR;
	private int prevC;

	private JPanel 			arrayP;
	private JPanel 			valuePanel;
	private Control			valueControl;

	private List<JLabel>	colLabels;
	private List<JLabel>	rowLabels;
	
	private JButton[][]		tabButtons;

	private Object[][]		tabValues;


	public Array (String label, String id, BaseType type, int width, int x, int y, int nbR, int nbC)
	{
		super(label, id, type, width, x, y);
		this.type	= type;
		this.oriC	= 0;
		this.oriR	= 0;

		// Les dimensions du tableau
		int tabWidth	= (Math.min(5, nbC) + 2) * 25;
		int tabHeight	= (Math.min(5, nbR) + 2) * 25;

		this.panel.setLayout( null );
		this.panel.setBounds( x, y, tabWidth + width + Control.LABEL_WIDTH + Array.gapX, tabHeight );

		this.typePanel.setBounds( x + width + Control.LABEL_WIDTH + tabWidth + Array.gapX, y, 100, Control.DFLT_HEIGHT );


		/*~~~~~~~~~~~~~~~~~~~~~*/
		/* Création du tableau */
		/*~~~~~~~~~~~~~~~~~~~~~*/

		/*  Label */
		this.labelL	= new JLabel( String.format("%s : ", this.label), SwingConstants.RIGHT );
		this.labelL.setBounds( 0, 0, Control.LABEL_WIDTH, Control.DFLT_HEIGHT );
		this.labelL.setForeground(Color.GRAY);

		this.panel.add( this.labelL );


		/* Tableau */
		this.colLabels		= new ArrayList<JLabel>();
		this.rowLabels		= new ArrayList<JLabel>();

		int clampedCol 		= Math.max( 0, Math.min(Array.maxCol, nbC) );
		int clampedRow 		= Math.max( 0, Math.min(Array.maxRow, nbR) );

		this.arrayP			= new JPanel( new GridLayout(clampedRow + 2, clampedCol + 2) );
		this.arrayP.setBounds( Control.LABEL_WIDTH, 0, tabWidth, tabHeight );
		this.arrayP.setBackground( Color.lightGray );
		this.arrayP.setBorder( new CompoundBorder(BorderFactory.createLineBorder(Color.black), new EmptyBorder(0,Array.gapX,0,0)) );

		this.panel.add( this.arrayP );


		/* Création du tableau logique */
		tabValues = new Object[nbR][nbC];


		Font baseFont	= this.panel.getFont();
		Font newFont	= baseFont.deriveFont(baseFont.getStyle() | Font.BOLD);

		/* Création du tableau sur l'interface */
		
		tabButtons = new JButton[maxRow][maxCol];
		for (int i = -1; i < clampedRow + 1; i++)
		{
			boolean inRowBounds = i >= 0 && i < clampedRow;

			// Ajout des labels des colonnes à la liste correspondante
			JLabel labelRow = null;
			if (inRowBounds)
			{
				labelRow = new JLabel( String.format("%d", clampedRow - i - 1) );
				this.rowLabels.add( labelRow );
			}
			else
			{
				labelRow = new JLabel();
			}
			this.arrayP.add( labelRow );

			/* CORPS */
			
			int maxRow = Array.maxRow;
			if (tabValues.length < Array.maxRow - 1 )
				maxRow = tabValues.length ;
			
			int maxCol = Array.maxCol ;
			if (tabValues[0].length < Array.maxCol - 1 )
				maxCol = tabValues.length;
			for (int j = 0; j < clampedCol; j++)
			{
				boolean inColBounds = j >= 0 && j < clampedCol;

				if (inColBounds)
				{
					if (inRowBounds)
					{
						JButton cellB = new JButton();
						tabButtons[clampedRow - i - 1][j] = cellB;
						cellB.addActionListener( new ArrayListener() );
						cellB.setActionCommand( String.format("%d;%d", clampedRow - i - 1, j) );
						cellB.setBackground(normalColor);
						this.arrayP.add( cellB );
					}
					else
					{
						// Ajout des labels des lignes à la liste correspondante
						JLabel colLabels = null;
						if (i >= 0)
						{
							colLabels = new JLabel( String.format("%d", j), SwingConstants.CENTER );
							this.colLabels.add( colLabels );
						}
						else
						{
							colLabels = new JLabel();
						}
						this.arrayP.add( colLabels );
					}
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

		this.valuePanel = new JPanel();
		this.valuePanel.setBackground( Frame.obtainFormColor() );
		this.valuePanel.setLayout( new GridLayout(2, 1) );
		this.valuePanel.setBounds( tabWidth + Control.LABEL_WIDTH + Array.gapX, (int) (tabHeight / 4f), width, (int) (tabHeight / 2f) );

		JLabel valueL = new JLabel( "Value :", SwingConstants.LEFT );
		valueL.setForeground( Color.GRAY );

		this.valuePanel.add( valueL );

		if (type == BaseType.Boolean)		this.valueControl	= new Checkbox("cdfz", width - 11, 0, 0);
		else								this.valueControl	= new Text("dzdz", type, width - 11, 0, 0);

		this.valuePanel.add( valueControl.getPanel() );


		this.panel.add( this.valuePanel );
	}

	public Array (String label, String id, BaseType type, int x, int y, int nbR, int nbC)
	{
		this(label, id, type, Control.DFLT_WIDTH, x, y, nbR, nbC);
	}

	/**
	 * Décale l'affichage du tableau
	 * @param deltaR Décalage en ligne
	 * @param deltaC Décalage en colonne
	 */
	private void shiftDisplay (int deltaR, int deltaC)
	{
		int oriRTemp = oriR;
		int oriCTemp = oriC;

		int maxRow = Array.maxRow - 1;
		if (tabValues.length < Array.maxRow - 1 )
			maxRow = tabValues.length -1;
		
		int maxCol = Array.maxCol - 1;
		if (tabValues[0].length < Array.maxCol - 1 )
			maxCol = tabValues.length -1;
		
		
		oriR = Math.max(0, Math.min(tabValues.length -1 - maxRow, oriR + deltaR));
		oriC = Math.max(0, Math.min(tabValues[0].length -1 - maxCol, oriC + deltaC));
		
		for (int i = 0; i < rowLabels.size(); i++)
			rowLabels.get(i).setText((oriR + rowLabels.size() - 1 - i) + "");

		for (int i = 0; i < colLabels.size(); i++)
			colLabels.get(i).setText((oriC + i) + "");
	}

	/**
	* Remet l'élément à son état initial
	*/
	@Override
	public void reset ()
	{
		for (Object[] row : tabValues)
			for (Object col : row)
				col = null;

		oriR = oriC = 0;
		// DEPLACER DANS LE TABLEAU (RESET QUOI)
	}

	/**
	 * Retourne la valeur contenu dans l'élément du formulaire
	 * @return La valeur rentrée par l'utilisateur dans cet élément
	 */
	@Override
	public Object getValues ()
	{
		return this.tabValues;
	}
}
